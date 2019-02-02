package top.yunshu.shw.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author itning
 */
public class PageContentUtils {
    private static final Logger logger = LoggerFactory.getLogger(PageContentUtils.class);

    private static Field contentField;

    private PageContentUtils() {

    }

    static {
        try {
            Class<?> name = Class.forName("org.springframework.data.domain.Chunk");
            contentField = name.getDeclaredField("content");
            contentField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            logger.error("error: ", e);
            System.exit(-1);
        }
    }

    public static Field getContentField() {
        return contentField;
    }
}
