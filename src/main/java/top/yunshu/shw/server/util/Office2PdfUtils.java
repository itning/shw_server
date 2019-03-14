package top.yunshu.shw.server.util;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.IWarningCallback;
import com.aspose.words.WarningType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Office to Pdf Utils
 *
 * @author itning
 */
public final class Office2PdfUtils {
    private static final Logger logger = LoggerFactory.getLogger(Office2PdfUtils.class);
    private static final String FONT_NAME = "SIMSUN";
    private static final String FONT_PATH = Office2PdfUtils.class.getResource("/").getPath().substring(1);

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
            case "docx":
                doWord2PDF(inputStream, outputStream);
                break;
            case "xls":
            case "xlsx":
                doExcel2PDF(inputStream, outputStream);
                break;
            case "ppt":
            case "pptx":
                doPowerPoint2PDF(inputStream, outputStream);
                break;
            default:
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

    private static void doExcel2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (!Office2PdfLicense.getExcelLicense()) {
            logger.error("doExcel2PDF Error: License Error");
            throw new RuntimeException("doExcel2PDF Error: License Error");
        }
        try {
            FontSettings.setFontsFolder(FONT_PATH, true);
            FontSettings.setDefaultFontName(FONT_NAME);
            Workbook wb = new Workbook(inputStream);
            wb.save(outputStream, com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            logger.error("FONT_PATH: " + FONT_PATH);
            logger.error("doExcel2PDF Error: ", e);
            throw new RuntimeException(e);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    private static void doWord2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (!Office2PdfLicense.getWordLicense()) {
            logger.error("doWord2PDF Error: License Error");
            throw new RuntimeException("doWord2PDF Error: License Error");
        }
        try {
            FontSettings.setFontsFolder(FONT_PATH, true);
            FontSettings.setDefaultFontName(FONT_NAME);
            Document doc = new Document(inputStream);
            IWarningCallback callback = info -> {
                if (info.getWarningType() == WarningType.FONT_SUBSTITUTION) {
                    logger.warn("Font substitution: " + info.getDescription());
                }
            };
            doc.setWarningCallback(callback);
            doc.save(outputStream, com.aspose.words.SaveFormat.PDF);
        } catch (Exception e) {
            logger.error("FONT_PATH: " + FONT_PATH);
            logger.error("doWord2PDF Error: ", e);
            throw new RuntimeException(e);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    private static void doPowerPoint2PDF(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (!Office2PdfLicense.getPPTLicense()) {
            logger.error("doPowerPoint2PDF Error: License Error");
            throw new RuntimeException("doPowerPoint2PDF Error: License Error");
        }
        try {
            FontSettings.setFontsFolder(FONT_PATH, true);
            FontSettings.setDefaultFontName(FONT_NAME);
            Presentation ppt = new Presentation(inputStream);
            ppt.save(outputStream, com.aspose.slides.SaveFormat.Pdf);
        } catch (Exception e) {
            logger.error("FONT_PATH: " + FONT_PATH);
            logger.error("doPowerPoint2PDF Error: ", e);
            throw new RuntimeException(e);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }
}
