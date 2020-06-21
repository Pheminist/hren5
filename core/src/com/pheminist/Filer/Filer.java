package com.pheminist.Filer;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Filer {
    private final String[] okFileExtensions = new String[]{"mid", "midi", "hren"};
    private final String[] internalFiles = new String[]{"OldMaple.mid", "SmokeOn4notes.mid",
            "SmokeOn7notes.mid", "MorozMoroz.mid"};
    private List<FileItem> fileItems;
    private List<FileItem> internalFileItems;

    private FileNameComparator fileNameComparator = new FileNameComparator();

    public Filer() {
        fileItems = new ArrayList<>();
        fileItems = getFileItems(getRoot());
        initInternalFileItems();
//        System.out.println("- root -  " + getRoot());
    }

    public FileHandle getRoot() {
        return Gdx.files.absolute(Gdx.files.getExternalStoragePath());
//        return Gdx.files.internal("");
    }

    public FileHandle getInternal() {
        return Gdx.files.internal("");
    }

    public List<FileItem> getFileItems(FileHandle path) {
        if (path.type() == Files.FileType.Internal) {
            return internalFileItems;
        }
        if (!path.isDirectory()) return null; //        if (!path.isDirectory) return arrayListOf()
        FileHandle[] files = path.list(new DirFilter());
        if (files == null) return fileItems;

        fileItems.clear();
//        if(path.file().getParent()!=null) {           /////////////////////////////////////////////////
        if (path.parent() != null) {
            FileItem fileItem = new FileItem(this, path.parent());
            fileItem.setName("/..");

            fileItems.add(fileItem); //todo uncomment

        }
        Arrays.sort(files, fileNameComparator);
        for (FileHandle file : files) {
            fileItems.add(new FileItem(this, file));
        }
        files = path.list(new HrenFileFilter());
        Arrays.sort(files, fileNameComparator);
        for (FileHandle file : files) {
            fileItems.add(new FileItem(this, file));
        }
        return fileItems;
    }

    public boolean isDir(FileHandle file) {
        return file.isDirectory();
    }

    public boolean isHren(File file) {
        for (String extension : okFileExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    private void initInternalFileItems() {
        internalFileItems = new ArrayList<>(internalFiles.length);
        for (String ifi : internalFiles) {
            internalFileItems.add(new FileItem(this, Gdx.files.internal("melodies/" + ifi)));
        }
    }

    private class HrenFileFilter implements FileFilter {
        public boolean accept(File file) {
            return isHren(file);
        }
    }

    private class DirFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    }

    class FileNameComparator implements Comparator<FileHandle> {
        public int compare(FileHandle a, FileHandle b) {
            return a.name().compareTo(b.name());
        }
    }
}
