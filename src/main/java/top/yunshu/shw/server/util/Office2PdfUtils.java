package top.yunshu.shw.server.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

/**
 * Office to Pdf Utils
 *
 * @author itning
 */
public class Office2PdfUtils {
    private Office2PdfUtils() {
    }

    /**
     * This method will convert office file to pdf file
     *
     * @param sourceFile Source File
     * @param targetFile Target File
     * @throws Exception If catch any exception, it will throws
     */
    public static void convert2Pdf(File sourceFile, File targetFile) throws Exception {
        String extensionName = sourceFile.getName().substring(sourceFile.getName().lastIndexOf(".") + 1);
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        switch (extensionName) {
            case "doc":
                doDOC2PDF(fileInputStream, fileOutputStream);
                break;
            case "docx":
                doDOCX2PDF(fileInputStream, fileOutputStream);
                break;
            case "xls":
                doXLS2PDF(fileInputStream, fileOutputStream);
                break;
            case "xlsx":
                doXLSX2PDF(fileInputStream, fileOutputStream);
                break;
            default:
                // Now we can`t convert ppt and pptx file to pdf
                // Default do nothing
        }
    }

    private static void doXLS2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        IConverter converter = LocalConverter.builder().build();
        converter
                .convert(inputStream)
                .as(DocumentType.XLS)
                .to(outputStream)
                .as(DocumentType.PDF)
                .execute();
        outputStream.close();
    }

    private static void doXLSX2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        IConverter converter = LocalConverter.builder().build();
        converter
                .convert(inputStream)
                .as(DocumentType.XLSX)
                .to(outputStream)
                .as(DocumentType.PDF)
                .execute();
        outputStream.close();
    }

    private static void doDOC2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        IConverter converter = LocalConverter.builder().build();
        converter
                .convert(inputStream)
                .as(DocumentType.DOC)
                .to(outputStream)
                .as(DocumentType.PDF)
                .execute();
        outputStream.close();
    }

    private static void doDOCX2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        IConverter converter = LocalConverter.builder().build();
        converter
                .convert(inputStream)
                .as(DocumentType.DOCX)
                .to(outputStream)
                .as(DocumentType.PDF)
                .execute();
        outputStream.close();
    }
}
