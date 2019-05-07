package top.itning.server.shwnotice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import top.itning.server.shwnotice.entity.Notice;

/**
 * 通知存储库
 *
 * @author itning
 * @date 2019/5/4 17:05
 */
public interface NoticeRepository extends ReactiveMongoRepository<Notice, String> {
}
