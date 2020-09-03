package com.aeione.ops.generic;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter  implements FilenameFilter {
    private final String fileExtension;

    public FileFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean accept(File directory, String fileName) {
        return (fileName.endsWith(this.fileExtension));
    }
}
