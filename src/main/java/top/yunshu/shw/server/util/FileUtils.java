package top.yunshu.shw.server.util;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.exception.FileException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 文件工具类
 *
 * @author itning
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String RANGE_SEPARATOR = "-";
    private static final String RANGE_CONTAINS = "bytes=";
    private static final int RANGE_BYTES_ALL = 2;
    private static final int RANGE_BYTES_ONE = 1;

    private FileUtils() {
    }

    /**
     * 获取文件扩展名
     *
     * @param file {@link MultipartFile}
     * @return 文件扩展名
     */
    public static String getExtensionName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extensionName = "";
        if (originalFilename != null) {
            int i = originalFilename.lastIndexOf(".");
            if (i != -1) {
                extensionName = file.getOriginalFilename().substring(i);
            }
        }
        return extensionName;
    }

    /**
     * 断点续传
     *
     * @param file        所需要下载的文件
     * @param contentType MIME类型
     * @param range       请求头
     * @param response    {@link HttpServletResponse}
     */
    public static void breakpointResume(File file, String contentType, String range, HttpServletResponse response) {
        long startByte = 0;
        long endByte = file.length() - 1;
        if (range != null && range.contains(RANGE_CONTAINS) && range.contains(RANGE_SEPARATOR)) {
            range = range.substring(range.lastIndexOf("=") + 1).trim();
            String[] ranges = range.split(RANGE_SEPARATOR);
            try {
                //判断range的类型
                if (ranges.length == RANGE_BYTES_ONE) {
                    if (range.startsWith(RANGE_SEPARATOR)) {
                        //类型一：bytes=-2343
                        endByte = Long.parseLong(ranges[0]);
                    } else if (range.endsWith(RANGE_SEPARATOR)) {
                        //类型二：bytes=2343-
                        startByte = Long.parseLong(ranges[0]);
                    }
                } else if (ranges.length == RANGE_BYTES_ALL) {
                    //类型三：bytes=22-2343
                    startByte = Long.parseLong(ranges[0]);
                    endByte = Long.parseLong(ranges[1]);
                }

            } catch (NumberFormatException e) {
                startByte = 0;
                endByte = file.length() - 1;
            }
        }
        long contentLength = endByte - startByte + 1;
        response.setHeader("Accept-Ranges", "bytes");
        if (range == null) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());
        }
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), StandardCharsets.ISO_8859_1));
        response.setHeader("Content-Length", String.valueOf(contentLength));
        long transmitted = 0;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
             BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buff = new byte[4096];
            int len = 0;
            randomAccessFile.seek(startByte);
            while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            if (transmitted < contentLength) {
                len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            outputStream.flush();
            response.flushBuffer();
            randomAccessFile.close();
            logger.debug("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (ClientAbortException e) {
            logger.debug("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (IOException e) {
            throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据文件扩展名获取MIME类型
     *
     * @param extensionName 扩展名
     * @return MIME类型
     */
    public static String getContentTypeByExtensionName(String extensionName) {
        switch (extensionName) {
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "doc":
                return "application/msword";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            default:
                return null;
        }
    }

    public static String getFileMD5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(IOUtils.toByteArray(fileInputStream));
            return Hex.encodeHexString(md5Bytes);
        } catch (Exception e) {
            logger.error("getFileMD5 method error: ", e);
            throw new RuntimeException(e);
        }
    }
}
