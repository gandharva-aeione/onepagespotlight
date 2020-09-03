package com.aeione.ops.generic;

import java.io.File;
import java.io.IOException;

public class FileHandling extends TestSetUp
{
    public static void createDirectoryByFolderName(String folderName) {
        try {
            String folderNamePath = userDirPath + "//" + folderName;
            File file = new File(folderNamePath);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
