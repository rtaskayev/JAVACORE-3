package com.netology;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Array of directories to be created
        List<String> dirsList = Arrays.asList("src", "res", "savegames", "temp", "src\\main", "src\\test", "res\\drawables", "res\\vectors", "res\\icons");
        // Array of files to be created
        List<String> filesList = Arrays.asList("src\\main\\Main.java", "src\\main\\Utils.java", "temp\\temp.txt");
        String dirPath = "D:\\Games\\"; // Working directory
        String logFilePath = dirPath + "temp\\temp.txt"; // Log file location

        StringBuilder output = new StringBuilder("Starting installation process...\n");

        // Create all directories from Array list
        for (String s : dirsList) {
            createDirectory(dirPath + s, output);
        }

        // Create all files from Array list
        for (String s : filesList) {
            createFile(dirPath + s, output);
        }

        output.append("INSTALLATION COMPLETED!\n");
        // Log all output to log file
        logToFile(logFilePath, output);
        System.out.println("Installation completed. Check " + logFilePath + " for more details");

    }

    // Method to create directory
    static void createDirectory(String path, StringBuilder output){

        File pathDir = new File(path);

        if (pathDir.mkdir()) {
            output.append(path).append(" - directory created\n");
        } else {
            output.append(path).append(" - ERROR, directory wasn`t created\n");
        }
    }

    // Method to create file
    static void createFile(String path, StringBuilder output) {

        File pathDir = new File(path);

        try {
            if (pathDir.createNewFile())
                output.append(path).append(" - file created\n");
        } catch (IOException ex) {
            output.append(ex.getMessage());
        }
    }

    // Method to put all output to log file
    static void logToFile(String path, StringBuilder output) {

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(String.valueOf(output));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
