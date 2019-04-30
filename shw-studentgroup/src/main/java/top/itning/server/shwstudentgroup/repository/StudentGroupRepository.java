package top.itning.server.shwstudentgroup.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwstudentgroup.entity.StudentGroup;

/**
 * @author itning
 * @date 2019/4/29 11:42
 */
public interface StudentGroupRepository extends ReactiveMongoRepository<StudentGroup, String> {
}
