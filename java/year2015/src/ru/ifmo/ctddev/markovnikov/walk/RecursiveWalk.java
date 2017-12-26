package ru.ifmo.ctddev.markovnikov.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class RecursiveWalk {
    private final static int INIT32 = 0x811c9dc5;
    private final static int PRIME32 = 0x01000193;
    private final static int BUFFER_SIZE = 2048;
    private final static String ZERO32 = "00000000";
    private static ArrayList<String> errorMessages = new ArrayList<>();
    private static BufferedWriter writer;
    private static Path outputFile;

    public static void main(String[] args) {
        Path inputFile;
        try {
            inputFile = Paths.get(args[0]);
            outputFile = Paths.get(args[1]);
        } catch (Exception e) {
            System.err.println("Check arguments");
            return;
        }
        BufferedReader reader;
        try {
            try {
                reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
            } catch (IOException openReaderException) {
                errorMessages.add("An I/O error occurred opening file " + inputFile.toString());
                throw openReaderException;
            } catch (SecurityException openReaderException) {
                errorMessages.add("Security error occurred opening file " + inputFile.toString());
                throw openReaderException;
            }
            try {

                try {
                    writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);
                } catch (IOException openWriterException) {
                    errorMessages.add("An I/O error occurred opening file " + outputFile.toString());
                    throw openWriterException;
                } catch (SecurityException openWriterException) {
                    errorMessages.add("Security error occurred opening file " + outputFile.toString());
                    throw openWriterException;
                }
                try {
                    String fileName;
                    while (true) {
                        try {
                            if ((fileName = reader.readLine()) == null)
                                break;
                        } catch (IOException readLineException) {
                            errorMessages.add("An I/O error occurred reading file " + inputFile.toString());
                            throw readLineException;
                        }
                        Path file = Paths.get(fileName);
                        if (!Files.isDirectory(file))
                            writeResult(file);
                        else
                            Files.walkFileTree(file, new FindVisitor());
                    }
                    writer.close();
                } catch (IOException readOrWriteException) {
                    try {
                        writer.close();
                    } catch (IOException closeWriterException) {
                        errorMessages.add("An I/O error occurred closing file " + outputFile.toString());
                        throw closeWriterException;
                    }
                    throw readOrWriteException;
                }
            } catch (IOException e) {
                try {
                    reader.close();
                } catch (IOException closeReaderException) {
                    errorMessages.add("An I/O error occurred closing file " + inputFile.toString());
                    throw closeReaderException;
                }
                throw e;
            }
        } catch (Exception e) {
            if (errorMessages.isEmpty())
                System.err.println("Unknown exception");
            for (String msg : errorMessages)
                System.err.println(msg);
        }
    }

    static void writeResult(Path file) throws IOException {
        String result = getFileHash(file) + " " + file.toString();
        try {
            writer.write(result);
            writer.newLine();
        } catch (IOException writeException) {
            errorMessages.add("An I/O error occurred writing to file " + outputFile.toString());
            throw writeException;
        }
    }

    static class FindVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            writeResult(file);
            return FileVisitResult.CONTINUE;
        }
    }

    private static String getFileHash(Path file) {
        int hash;
        InputStream reader = null;
        try {
            reader = Files.newInputStream(file);
            int r;
            hash = INIT32;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((r = reader.read(buffer)) != -1) {
                for (int i = 0; i < r; i++) {
                    byte ch = buffer[i];
                    hash *= PRIME32;
                    hash ^= ch & 0xff;
                }

            }
            reader.close();
        } catch (IOException | SecurityException e) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignored) {
            }
            return ZERO32;
        }
        return String.format("%08x", hash);
    }
}
