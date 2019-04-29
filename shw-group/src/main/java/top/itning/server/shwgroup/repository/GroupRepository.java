package top.itning.server.shwgroup.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwgroup.entity.Group;

/**
 * @author itning
 * @date 2019/4/29 11:42
 */
public interface GroupRepository extends ReactiveMongoRepository<Group, String> {
}
