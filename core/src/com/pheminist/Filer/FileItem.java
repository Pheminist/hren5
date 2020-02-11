package com.pheminist.Filer;

import com.badlogic.gdx.files.FileHandle;

public class FileItem {
    final public static int DIR_TYPE = 1;
    final public static int HREN_TYPE = 2;
    final public static int UNKNOWN_TYPE = 0;

//    private int iconResource;
    private String name;
    private int type;
    private FileHandle file;

    public FileItem(Filer filer, FileHandle file) {
        this.file = file;
        this.name = file.name();
        if (filer.isDir(file)) {
            type = DIR_TYPE;
//            iconResource = R.drawable.dir_icon;
        } else if(filer.isHren(file.file())) {
            type = HREN_TYPE;
//            iconResource = R.drawable.hren_icon;
        } else {
            type = UNKNOWN_TYPE;
//            iconResource = R.drawable.hren_icon; //todo change the icon
        }
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public FileHandle getFile() {
        return file;
    }

    public void setName(String name) {
        this.name = name;
    }
}
