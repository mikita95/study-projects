package ru.ifmo.ctddev.markovnikov.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Walk {


    private final static int INIT32 = 0x811c9dc5;
    private final static int PRIME32 = 0x01000193;
    private final static String ZERO32 = "00000000";

    public static void main(String[] args) {
        Path inputFile;
        Path outputFile;
        try {
            inputFile = Paths.get(args[0]);
            outputFile = Paths.get(args[1]);
        } catch (Exception e) {
            System.err.println("Check arguments");
            return;
        }
        BufferedReader reader;
        ArrayList<String> errorMessages = new ArrayList<>();
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
                BufferedWriter writer;
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
                        String result = getFileHash(fileName) + " " + fileName;
                        try {
                            writer.write(result);
                            writer.newLine();
                        } catch (IOException writeException) {
                            errorMessages.add("An I/O error occurred writing to file " + outputFile.toString());
                            throw writeException;
                        }
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

    private static String getFileHash(String fileName) {
        Path path;
        try {
            path = Paths.get(fileName);
        } catch (InvalidPathException e) {
            return ZERO32;
        }
        int hash;
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(path);
            int r;
            hash = INIT32;
            byte[] buffer = new byte[2048];
            while ((r = inputStream.read(buffer)) != -1) {
                for (int i = 0; i < r; i++) {
                    byte ch = buffer[i];
                    hash *= PRIME32;
                    hash ^= ch & 0xff;
                }
            }
            inputStream.close();
        } catch (IOException | SecurityException e) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {
            }
            return ZERO32;
        }
        return String.format("%08x", hash);
    }

}
