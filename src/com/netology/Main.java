package com.netology;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        String dirPath = "D:\\Games\\"; // Working directory
        String saveFiles = dirPath + "savegames\\"; // Directory to zip files in
        String zipFile = saveFiles + "zip.zip"; // Output zip file
        int fileSaveCount = 1; // Count for files naming
        List<String> filesList; // List of files to archive and delete

        GameProgress gameProgress1 = new GameProgress(60, 20, 3, 4);
        GameProgress gameProgress2 = new GameProgress(7, 40, 11, 2);
        GameProgress gameProgress3 = new GameProgress(88, 10, 7, 6);

        // Creating Serialized game progress files
        saveGame(saveFiles + "save" + fileSaveCount + ".dat", gameProgress1);
        fileSaveCount++;
        saveGame(saveFiles + "save" + fileSaveCount + ".dat", gameProgress2);
        fileSaveCount++;
        saveGame(saveFiles + "save" + fileSaveCount + ".dat", gameProgress3);
        fileSaveCount++;

        // Getting list of saved files in directory
        filesList = getListOfFilesToZip(saveFiles);

        // Archive and delete archived files
        if (zipFiles(zipFile, filesList)) {
            deleteArchivedFiles(filesList);
            System.out.println("Archive and deletion completed");
        } else {
            System.out.println("Archive failed");
        }

    }


    // Method to delete archived files
    static void deleteArchivedFiles(List filesToDelete) {
        for (int i = 0; i < filesToDelete.size(); i++) {
            try {
                File file = new File((String) filesToDelete.get(i));
                file.delete();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Method to get list of files to archive
    static List<String> getListOfFilesToZip(String dirPath) {
        List<String> filesList = new ArrayList<>();
        File dir = new File(dirPath);

        for (File item : dir.listFiles()) {
            if (item.isFile()) {
                filesList.add(item.getAbsolutePath());
            }
        }
        return filesList;
    }

    // Method to write game progress
    static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Method to archive files
    static boolean zipFiles(String archiveFilePath, List filesToArchive) {

            try (FileOutputStream fout = new FileOutputStream(archiveFilePath);
                 ZipOutputStream zout = new ZipOutputStream(fout)) {

                for (int i = 0; i < filesToArchive.size(); i++) {

                    File fileToZip = new File((String) filesToArchive.get(i));
                    try (FileInputStream fis = new FileInputStream(fileToZip)) {
                        ZipEntry entry = new ZipEntry(fileToZip.getName());
                        zout.putNextEntry(entry);
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zout.write(buffer);
                        zout.closeEntry();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        return true;
    }

}
