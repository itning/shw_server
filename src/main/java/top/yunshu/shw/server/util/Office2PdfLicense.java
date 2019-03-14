package top.yunshu.shw.server.util;

import java.io.FileInputStream;
import java.io.InputStream;


/**
 * @author itning
 */
final class Office2PdfLicense {
    private static final String LICENSE_PATH = Office2PdfLicense.class.getResource("/license.xml").getPath().substring(1);

    /**
     * 获取Word的license签字验证
     */
    static boolean getWordLicense() {
        boolean result = false;
        try (InputStream is = new FileInputStream(LICENSE_PATH)) {
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取Excel的license签字验证
     */
    static boolean getExcelLicense() {
        boolean result = false;
        try (InputStream is = new FileInputStream(LICENSE_PATH)) {
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取PPT的license
     */
    static boolean getPPTLicense() {
        boolean result = false;
        try (InputStream is = new FileInputStream(LICENSE_PATH)) {
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
