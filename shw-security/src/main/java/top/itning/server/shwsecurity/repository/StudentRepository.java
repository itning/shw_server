package top.itning.server.shwsecurity.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwsecurity.entity.Student;

/**
 * 学生存储库
 *
 * @author itning
 * @date 2019/4/30 13:10
 */
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
}
