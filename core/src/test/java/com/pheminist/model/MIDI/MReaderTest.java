package com.pheminist.model.MIDI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class MReaderTest {
    private byte[] testBytes;// = {0,15,0xF,100};

    @Before
    public void setUp() throws Exception {

        String path = "src/test/res/test.hren";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

 //       System.out.println(absolutePath);
        assertTrue(file.exists());
        testBytes = Files.readAllBytes(file.toPath());

    }
// 00 80 F1 FF FF 77
    @Test
    public void readByte() {
        MReader mReader = new MReader(testBytes);
        Assert.assertEquals(mReader.readByte(),0);
        Assert.assertEquals(mReader.readByte(),0x80);
        Assert.assertEquals(mReader.readByte(),0xF1);
        Assert.assertEquals(mReader.readByte(),0xFF);
        Assert.assertEquals(mReader.readByte(),255);

    }

    @Test
    public void read16BigEndian() {
        MReader mReader = new MReader(testBytes);
        Assert.assertEquals(mReader.read16BigEndian(),128);
        Assert.assertEquals(mReader.read16BigEndian(),61951);
    }

    @Test
    public void read24BigEndian() {
        MReader mReader = new MReader(testBytes);
        Assert.assertEquals(mReader.read24BigEndian(),33009);
        mReader.setIndex(1);
        Assert.assertEquals(mReader.read24BigEndian(),8450559);
    }

    @Test
    public void read32BigEndian() {
        MReader mReader = new MReader(testBytes);
        Assert.assertEquals(mReader.read32BigEndian(),8450559);
        mReader.setIndex(2);
        Assert.assertEquals(mReader.read32BigEndian(),4060086135L);
    }

    @Test
    public void readVarLengthInt() {
        MReader mReader = new MReader(testBytes);
        Assert.assertEquals(mReader.readVarLengthInt(),0);
        mReader.setIndex(2);
        Assert.assertEquals(mReader.readVarLengthInt(),0b1110001111111111111111110111);
    }
}