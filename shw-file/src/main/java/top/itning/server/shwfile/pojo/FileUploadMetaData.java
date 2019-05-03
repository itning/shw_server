package top.itning.server.shwfile.pojo;

/**
 * @author itning
 * @date 2019/5/3 11:15
 */
public class FileUploadMetaData {
    /**
     * 学生学号
     */
    private String studentId;
    /**
     * 上传的文件所属的作业ID
     */
    private String workId;
    /**
     * 文件MIME类型
     */
    private String mime;
    /**
     * 扩展名
     */
    private String extensionName;
    /**
     * 大小
     */
    private long size;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileUploadMetaData{" +
                "studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                ", mime='" + mime + '\'' +
                ", extensionName='" + extensionName + '\'' +
                ", size=" + size +
                '}';
    }
}
