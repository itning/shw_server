package top.yunshu.shw.server.util;

import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 压缩文件(Zip)
 *
 * @author itning
 */
public final class ZipCompressedFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZipCompressedFileUtils.class);

    private static final Gson GSON = new Gson();
    private File file;
    private List<Node> list;
    private Charset charset;

    public static ZipCompressedFileUtils getInstance(File file) {
        return new ZipCompressedFileUtils(file);
    }

    private ZipCompressedFileUtils(File file) {
        this.file = file;
        this.list = new ArrayList<>();
        this.charset = Charset.forName("gbk");
    }

    public ZipCompressedFileUtils changeCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * read zip file
     *
     * @return ZipCompressedFileUtils
     */
    public ZipCompressedFileUtils readZipFile() {
        try (ZipFile zf = new ZipFile(file, charset)) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.isDirectory()) {
                    logger.debug("D: " + zipEntry.getName());
                    int i = 0;
                    Node temp = null;
                    for (String dir : getArrayOfDir(zipEntry.getName())) {
                        if (i == 0) {
                            temp = createRootDir(dir, list);
                            i++;
                        } else {
                            temp = addNewDir(dir, temp);
                            i++;
                        }
                    }
                } else {
                    logger.debug("F: " + zipEntry.getName());
                    String[] dirOfFile = getDirOfFile(zipEntry.getName());
                    if (dirOfFile.length == 0) {
                        list.add(new Node().setFile(zipEntry));
                    } else {
                        addCompressedFileByDir(dirOfFile, list, zipEntry);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * preview
     *
     * @param name         name
     * @param outputStream OutputStream
     */
    public void preview(String name, OutputStream outputStream) {
        try (ZipFile zf = new ZipFile(file, charset);
             InputStream in = new BufferedInputStream(new FileInputStream(file));
             ZipInputStream zin = new ZipInputStream(in, charset)) {
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (!ze.isDirectory() && ze.getName().equals(name)) {
                    InputStream inputStream = zf.getInputStream(ze);
                    IOUtils.copy(inputStream, outputStream);
                }
            }
        } catch (Exception e) {
            logger.error("zip preview error: ", e);
        }
    }

    /**
     * get list of json
     *
     * @return json
     */
    public String getJson() {
        return GSON.toJson(list);
    }

    /**
     * add Compressed File By Dir
     *
     * @param dir      dir array
     * @param nodeList list of node
     * @param zipEntry ZipEntry
     */
    private void addCompressedFileByDir(String[] dir, List<Node> nodeList, ZipEntry zipEntry) {
        List<Node> temp = nodeList;
        int i = 0;
        while (true) {
            int finalI = i;
            Node node = temp.stream().filter(c -> c.getText().equals(dir[finalI])).collect(Collectors.toList()).get(0);
            if (node.getText().equals(dir[dir.length - 1])) {
                node.getChildren().add(new Node().setFile(zipEntry));
                break;
            }
            temp = node.getChildren();
            i++;
        }
    }

    /**
     * create root dir
     *
     * @param dirName  dir array
     * @param nodeList node of list
     * @return Node
     */
    private Node createRootDir(String dirName, List<Node> nodeList) {
        List<Node> collect = nodeList.stream().filter(c -> c.getText().equals(dirName)).collect(Collectors.toList());
        if (collect.isEmpty()) {
            Node node = new Node();
            node.setFile(false);
            node.setText(dirName);
            nodeList.add(node);
            return node;
        } else {
            return collect.get(0);
        }
    }

    /**
     * add new dir
     *
     * @param dirName    dir name
     * @param parentNode parent node
     * @return Node
     */
    private Node addNewDir(String dirName, Node parentNode) {
        List<Node> collect = parentNode.getChildren().stream().filter(c -> c.getText().equals(dirName)).collect(Collectors.toList());
        if (collect.isEmpty()) {
            Node c = new Node();
            c.setFile(false);
            c.setText(dirName);
            parentNode.getChildren().add(c);
            return c;
        } else {
            return collect.get(0);
        }
    }

    /**
     * return file`s parent dir.if none have,return <code>[]</code>
     *
     * @param name file
     * @return array of string
     */
    private String[] getDirOfFile(String name) {
        String[] temp = name.split("/");
        return Arrays.copyOf(temp, temp.length - 1);
    }

    /**
     * get file name
     *
     * @param name name
     * @return file name
     */
    private String getFileName(String name) {
        String[] array = name.split("/");
        return array[array.length - 1];
    }

    /**
     * get array of dir
     *
     * @param name name
     * @return dir array
     */
    private String[] getArrayOfDir(String name) {
        return name.split("/");
    }

    /**
     * This is Node Of Zip File
     */
    class Node implements Serializable {
        private List<Node> children = new ArrayList<>();
        private String text;
        private boolean isFile;
        private Long size;
        private String filePath;

        Node setFile(ZipEntry zipEntry) {
            this.setFile(true);
            this.setText(getFileName(zipEntry.getName()));
            this.setChildrenIsNull();
            this.setSize(zipEntry.getSize());
            this.setFilePath(zipEntry.getName());
            return this;
        }

        List<Node> getChildren() {
            return children;
        }

        void setChildrenIsNull() {
            this.children = null;
        }

        String getText() {
            return text;
        }

        void setText(String text) {
            this.text = text;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }

        public boolean isFile() {
            return isFile;
        }

        void setFile(boolean file) {
            isFile = file;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
