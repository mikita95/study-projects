package ru.ifmo.ctddev.markovnikov.uifilecopy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Utility that copies files and directories to specified directory. Shows time and speed of copying.
 *
 * @author Nikita Markovnikov
 */

public class UIFileCopy extends JFrame implements ActionListener, PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton buttonCopy;
    private JLabel totalTime;
    private JLabel remainingTime;
    private JLabel averageSpeed;
    private JLabel currentSpeed;

    private CopyWorker task;

    private static Path source;
    private static Path target;

    /**
     * Create a new instance of {@link ru.ifmo.ctddev.markovnikov.uifilecopy.UIFileCopy} and draw UI.
     */
    public UIFileCopy() {
        createGUI();
    }

    /**
     * Method that processes event after pressing button
     *
     * @param e Action that happened
     * @see java.awt.event.ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Copy".equals(buttonCopy.getText())) {
            File sourceFile = source.toFile();
            File targetFile = target.toFile();
            if (!sourceFile.exists()) {
                JOptionPane.showMessageDialog(this, "The source file/directory does not exist!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!targetFile.exists()) targetFile.mkdirs();
            else {
                int option = JOptionPane.showConfirmDialog(this, "The target file/directory already exists, do you want to overwrite it?", "Overwrite the target", JOptionPane.YES_NO_OPTION);
                if (option != JOptionPane.YES_OPTION) return;
            }
            task.execute();
            buttonCopy.setText("Cancel");
        } else if ("Cancel".equals(buttonCopy.getText())) {
            task.cancel(true);
            System.exit(0);
        }
    }

    /**
     * Method that listens changes in SwingWorker
     *
     * @param evt Property that was changed
     * @see java.beans.PropertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            progressBar.setValue((Integer) evt.getNewValue());
        }
    }

    private class Chunk {
        long allBytes;
        long deltaBytes;
        long allTime;
        long deltaTime;

        public Chunk() {
            this.allBytes = 0;
            this.deltaBytes = 0;
            this.allTime = 0;
            this.deltaTime = 0;
        }

        public Chunk(long allBytes, long deltaBytes, long allTime, long deltaTime) {
            this.allBytes = allBytes;
            this.deltaBytes = deltaBytes;
            this.allTime = allTime;
            this.deltaTime = deltaTime;
        }

        public Chunk(long allBytes, long allTime) {
            this.allBytes = allBytes;
            this.allTime = allTime;
            this.deltaTime = 0;
            this.deltaBytes = 0;
        }
    }

    private class CopyWorker extends SwingWorker<Void, Chunk> {
        private File source;
        private File target;

        private long totalBytes = 0L;
        private long copiedBytes = 0L;
        private long startTime = 0L;

        private String getTimeFormat(long millis) {
            long second = (millis / 1000) % 60;
            long minute = (millis / (1000 * 60)) % 60;
            long hour = (millis / (1000 * 60 * 60)) % 24;
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }

        private String getSpeedFormat(long bytes, long millis) {
            if (millis == 0)
                millis = 1;
            float speed = ((float) (bytes / millis * 1000) / 1024 / 1024);
            return String.format("%.2f mB/s", speed);
        }

        public CopyWorker(File source, File target) {
            this.source = source;
            this.target = target;
            progressBar.setValue(0);
        }

        @Override
        public Void doInBackground() throws Exception {
            if (!isCancelled()) {
                getTotalSize(source);
                copyFiles(source, target, 0);
            }
            return null;
        }

        @Override
        protected void process(List<Chunk> chunks) {
            if (!isCancelled()) {
                Chunk chunk = chunks.get(chunks.size() - 1);
                totalTime.setText("Total time: " + getTimeFormat(chunk.allTime));
                String aSpeed = getSpeedFormat(chunk.allBytes, chunk.allTime);
                averageSpeed.setText("Average speed: " + aSpeed);
                try {
                    String rTime = getTimeFormat(totalBytes / (chunk.allBytes / chunk.allTime) - chunk.allTime);
                    remainingTime.setText("Remaining time: " + rTime);
                } catch (Exception ignored) {
                }
                if (chunk.deltaBytes != 0)
                    currentSpeed.setText("Current speed: " + getSpeedFormat(chunk.deltaBytes, chunk.deltaTime));
            }
        }

        @Override
        public void done() {
            setProgress(100);
            System.exit(0);
        }

        private void getTotalSize(File sourceFile) {
            if (!sourceFile.isDirectory()) {
                totalBytes += sourceFile.length();
            }
            File[] files = sourceFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) getTotalSize(file);
                    else totalBytes += file.length();
                }
            }
        }

        private void copyFiles(File sourceFile, File targetFile, int depth) throws IOException {
            if (isCancelled())
                return;
            if (sourceFile.isDirectory()) {
                if (!targetFile.exists()) targetFile.mkdirs();
                String[] filePaths = sourceFile.list();
                for (String filePath : filePaths) {
                    File srcFile = new File(sourceFile, filePath);
                    File destFile = new File(targetFile, filePath);
                    copyFiles(srcFile, destFile, depth++);
                }
            } else {
                BufferedInputStream bufferedInputStream;
                BufferedOutputStream bufferedOutputStream;
                if (depth == 0) {
                    if (!targetFile.exists()) targetFile.mkdirs();
                    File srcFile = new File(sourceFile.getPath());
                    File destFile = new File(targetFile.getPath(), sourceFile.getName());
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                } else {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(sourceFile));
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
                }
                int length;
                byte[] buffer = new byte[4096];

                long localBytes = 0;
                long localFinish;

                long localStart = System.currentTimeMillis();

                if (startTime == 0)
                    startTime = localStart;

                while ((((length = bufferedInputStream.read(buffer)) != -1)) && (!isCancelled())) {
                    bufferedOutputStream.write(Arrays.copyOf(buffer, length));

                    copiedBytes += length;
                    localBytes += length;

                    setProgress((int) (this.copiedBytes * 100 / totalBytes));
                    localFinish = System.currentTimeMillis();

                    if ((localFinish - localStart) >= 500) {
                        publish(new Chunk(copiedBytes, localBytes, localFinish - startTime, localFinish - localStart));
                        localBytes = 0;
                        localStart = System.currentTimeMillis();
                    } else {
                        publish(new Chunk(copiedBytes, localFinish - startTime));
                    }
                }
                localFinish = System.currentTimeMillis();
                publish(new Chunk(copiedBytes, localBytes, localFinish - startTime, localFinish - localStart));
                bufferedInputStream.close();
                bufferedOutputStream.close();
            }
        }
    }

    private void createGUI() {
        setTitle("UIFileCopy");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (task != null)
                    task.cancel(true);
                System.exit(0);
            }
        });

        JLabel lblSource = new JLabel("Source Path: " + source);
        JLabel lblTarget = new JLabel("Target Path: " + target);
        JLabel lblProgressAll = new JLabel("All: ");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        totalTime = new JLabel("Total time: ");
        remainingTime = new JLabel("Remaining time: ");
        averageSpeed = new JLabel("Average speed: ");
        currentSpeed = new JLabel("Current speed: ");

        task = this.new CopyWorker(source.toFile(), target.toFile());
        task.addPropertyChangeListener(this);

        buttonCopy = new JButton("Copy");
        buttonCopy.setFocusPainted(false);
        buttonCopy.setEnabled(true);
        buttonCopy.addActionListener(this);

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panelInputLabels = new JPanel(new BorderLayout(0, 5));
        JPanel panelProgressLabels = new JPanel(new BorderLayout(0, 5));
        JPanel panelProgressBars = new JPanel(new BorderLayout(0, 5));
        JPanel panelDetailsLabels = new JPanel();
        panelDetailsLabels.setLayout(new BoxLayout(panelDetailsLabels, BoxLayout.Y_AXIS));

        panelInputLabels.add(lblSource, BorderLayout.NORTH);
        panelInputLabels.add(lblTarget, BorderLayout.CENTER);
        panelProgressLabels.add(lblProgressAll, BorderLayout.NORTH);
        panelProgressBars.add(progressBar, BorderLayout.NORTH);

        panelDetailsLabels.add(totalTime);
        panelDetailsLabels.add(remainingTime);
        panelDetailsLabels.add(averageSpeed);
        panelDetailsLabels.add(currentSpeed);

        JPanel panInput = new JPanel(new BorderLayout(0, 5));
        panInput.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JPanel panProgress = new JPanel(new BorderLayout(0, 5));
        panProgress.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Progress"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JPanel panControls = new JPanel(new BorderLayout());
        panControls.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JPanel panDetails = new JPanel(new BorderLayout());
        panDetails.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Details"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        panInput.add(panelInputLabels, BorderLayout.LINE_START);
        panProgress.add(panelProgressLabels, BorderLayout.LINE_START);
        panProgress.add(panelProgressBars, BorderLayout.CENTER);
        panControls.add(buttonCopy, BorderLayout.NORTH);
        panDetails.add(panelDetailsLabels, BorderLayout.LINE_START);

        JPanel panUpper = new JPanel(new BorderLayout());
        panUpper.add(panInput, BorderLayout.NORTH);
        panUpper.add(panProgress, BorderLayout.CENTER);
        panUpper.add(panDetails, BorderLayout.SOUTH);
        contentPane.add(panUpper, BorderLayout.NORTH);
        contentPane.add(panControls, BorderLayout.CENTER);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    /**
     * Main method that launch utility
     *
     * @param args Arguments of command line
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("java UIFileCopy <source> <target>");
            System.exit(-1);
        }

        source = Paths.get(args[0]);
        target = Paths.get(args[1]);

        SwingUtilities.invokeLater(() -> new UIFileCopy().setVisible(true));
    }

}