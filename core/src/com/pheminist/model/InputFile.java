package com.pheminist.model;

import com.badlogic.gdx.files.FileHandle;

public class InputFile {
    private FileHandle file;

    public InputFile(FileHandle file) {
        this.file = file;
    }

    public String getBareName(){
        return file.nameWithoutExtension();
    }

    public FileHandle getFile() {
        return file;
    }

    public void setFile(FileHandle file) {
        this.file = file;
    }
}
