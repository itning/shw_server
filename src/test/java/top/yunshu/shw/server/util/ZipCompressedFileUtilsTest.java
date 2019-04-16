package top.yunshu.shw.server.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ZipCompressedFileUtilsTest {

    @Test
    public void getInstance() throws FileNotFoundException {
        String json = ZipCompressedFileUtils
                .getInstance()
                .readZipFileFromInputStream(new FileInputStream("G:\\winutils.zip"))
                .getJson();
        System.out.println(json);
    }
}