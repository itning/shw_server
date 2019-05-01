package top.itning.server.shwwork;

import org.junit.Test;
import top.itning.server.shwwork.entity.Work;

/**
 * @author itning
 * @date 2019/5/1 10:52
 */
public class TestJava {
    @Test
    public void test() {
        Work work = new Work();
        work.setGroupId("1");
        work.setEnabled(true);
        System.out.println(work);

        Work clones = work.clones();
        System.out.println(clones);

        work.setEnabled(false);
        work.setGroupId("2");

        System.out.println(clones);
        System.out.println(work);
    }
}
