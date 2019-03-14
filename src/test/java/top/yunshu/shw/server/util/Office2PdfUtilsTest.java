package top.yunshu.shw.server.util;

import org.junit.Test;

import java.io.File;

public class Office2PdfUtilsTest {
    @Test
    public void testDocx2Pdf() throws Exception {
        String input = "C:\\Users\\wangn\\Desktop\\";
        String output = "C:\\Users\\wangn\\Desktop\\";
        dos(input + "doc.doc", output + "doc.pdf");

        dos(input + "docx.docx", output + "docx.pdf");

        dos(input + "xls.xls", output + "xls.pdf");

        dos(input + "xlsx.xlsx", output + "xlsx.pdf");

        dos(input + "ppt.ppt", output + "ppt.pdf");

        dos(input + "pptx.pptx", output + "pptx.pdf");

    }

    @Test
    public void testMD5() {
        String inputFile = "C:\\Users\\wangn\\Desktop\\aa.doc";
        String fileMD5 = FileUtils.getFileMD5(new File(inputFile));
        System.out.println(fileMD5);
    }

    private static void dos(String inputFile, String outputFile) throws Exception {
        Office2PdfUtils.convert2Pdf(new File(inputFile), new File(outputFile));
    }
}
