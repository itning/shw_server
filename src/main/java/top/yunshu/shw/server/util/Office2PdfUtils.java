package top.yunshu.shw.server.util;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;

/**
 * Office to Pdf Utils
 *
 * @author itning
 */
public class Office2PdfUtils {
    private static final Logger logger = LoggerFactory.getLogger(Office2PdfUtils.class);
    private static final String FONT_PATH = Office2PdfUtils.class.getResource("/SIMHEI.TTF").getPath().substring(1);

    private Office2PdfUtils() {
    }

    /**
     * This method will convert office file to pdf file
     *
     * @param inputStream   Input Stream
     * @param outputStream  Output Stream
     * @param extensionName File extension name
     * @throws Exception If catch any exception, it will throws
     */
    public static void convert2Pdf(InputStream inputStream, OutputStream outputStream, String extensionName) throws Exception {
        switch (extensionName) {
            case "doc":
                doDOC2PDF(inputStream, outputStream);
                break;
            case "docx":
                doDOCX2PDF(inputStream, outputStream);
                break;
            case "xls":
                doXLS2PDF(inputStream, outputStream);
                break;
            case "xlsx":
                doXLSX2PDF(inputStream, outputStream);
                break;
            default:
                // Now we can`t convert ppt and pptx file to pdf
                // Default do nothing
        }
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
        convert2Pdf(fileInputStream, fileOutputStream, extensionName);
    }

    private static void doXLS2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {

    }

    private static void doXLSX2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {

    }

    private static void doDOC2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {

    }

    private static void doDOCX2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        logger.debug("font path: " + FONT_PATH);
        ITextFontRegistry iTextFontRegistry = new ITextFontRegistry() {
            @Override
            public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                BaseFont base = null;
                try {
                    base = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                return new Font(base, size, style, color);
            }
        };
        XWPFDocument document = new XWPFDocument(inputStream);
        PdfConverter.getInstance().convert(document, outputStream, PdfOptions.create().fontProvider(iTextFontRegistry));
    }
}
