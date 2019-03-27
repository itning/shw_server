package top.yunshu.shw.server.util;

import top.yunshu.shw.server.dao.StudentDao;
import top.yunshu.shw.server.dao.WorkDao;

/**
 * 文件名规范
 *
 * @author itning
 */

public final class FileNameSpecificationUtils {
    private FileNameSpecificationUtils() {

    }

    /**
     * 姓名+学号
     */
    public static final String NAME_AND_STUDENT_NUMBER = "0";
    /**
     * 学号+姓名
     */
    public static final String STUDENT_NUMBER_AND_NAME = "1";
    /**
     * 学号
     */
    public static final String ONLY_STUDENT_NUMBER = "2";
    /**
     * 姓名
     */
    public static final String ONLY_NAME = "3";

    /**
     * 获取文件名(不含扩展名)
     *
     * @param studentNo     学号
     * @param studentName   姓名
     * @param specification 规范
     * @return 文件名
     */
    public static String getFileName(String studentNo, String studentName, String specification) {
        StringBuilder stringBuilder = new StringBuilder(18);
        String fileName;
        switch (specification) {
            case NAME_AND_STUDENT_NUMBER: {
                fileName = stringBuilder.append(studentName).append(studentNo).toString();
                break;
            }
            case STUDENT_NUMBER_AND_NAME: {
                fileName = stringBuilder.append(studentNo).append(studentName).toString();
                break;
            }
            case ONLY_NAME: {
                fileName = stringBuilder.append(studentName).toString();
                break;
            }
            case ONLY_STUDENT_NUMBER: {
                fileName = stringBuilder.append(studentNo).toString();
                break;
            }
            default: {
                fileName = stringBuilder.append(studentName).append(studentNo).toString();
            }
        }
        return fileName;
    }

    public static String[] safeGetStudentNameAndFileNameFormat(StudentDao studentDao, WorkDao workDao, String studentNumber, String workId) {
        String studentName = studentDao.findNameByNo(studentNumber);
        String fileNameFormat = workDao.findFileNameFormatById(workId);
        if (fileNameFormat == null) {
            fileNameFormat = "";
        }
        if (studentName == null) {
            studentName = "";
        }
        return new String[]{studentName, fileNameFormat};
    }
}
