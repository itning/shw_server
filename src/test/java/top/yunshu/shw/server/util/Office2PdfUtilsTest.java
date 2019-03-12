package top.yunshu.shw.server.util;

import org.junit.Test;

import java.io.File;

public class Office2PdfUtilsTest {
    @Test
    public void testDocx2Pdf() throws Exception {
        String inputFile = "C:\\Users\\wangn\\Desktop\\bb.docx";
        String outputFile = "C:\\Users\\wangn\\Desktop\\TEST.pdf";
        Office2PdfUtils.convert2Pdf(new File(inputFile), new File(outputFile));
    }
}
