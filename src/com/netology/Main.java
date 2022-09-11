package com.netology;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        String dirPath = "D:\\Games\\"; // Working directory
        String saveFiles = dirPath + "savegames\\"; // Directory to zip files in
        String zipFile = saveFiles + "zip.zip"; // Output zip file

        // Unzip archived files
        openZip(zipFile, saveFiles);
        // Print Deserialized object data
        System.out.println(openProgress("D:\\Games\\savegames\\save1.dat"));

    }

    // Method to Deserialize object
    static GameProgress openProgress(String fileToOpen) {

        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(fileToOpen);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    // Method to unzip files
    static void openZip(String archiveFilePath, String unzipDirPath) {

        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(archiveFilePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = unzipDirPath + entry.getName(); // getting file name
                // Unzipping
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
