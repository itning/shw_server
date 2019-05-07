package top.itning.server.shwwork.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwwork.entity.Work;

/**
 * 作业存储库
 *
 * @author itning
 * @date 2019/5/1 9:50
 */
public interface WorkRepository extends ReactiveMongoRepository<Work, String> {
}
