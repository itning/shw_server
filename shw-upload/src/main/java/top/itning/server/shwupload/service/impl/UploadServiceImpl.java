package top.itning.server.shwupload.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwupload.client.WorkClient;
import top.itning.server.shwupload.client.entity.Work;
import top.itning.server.shwupload.entity.Upload;
import top.itning.server.shwupload.repository.UploadRepository;
import top.itning.server.shwupload.service.UploadService;
import top.itning.server.shwupload.stream.UploadMessage;
import top.itning.server.shwupload.util.ReactiveMongoHelper;

/**
 * @author itning
 * @date 2019/5/2 16:56
 */
@Service
public class UploadServiceImpl implements UploadService {
    private final UploadRepository uploadRepository;
    private final ReactiveMongoHelper reactiveMongoHelper;
    private final UploadMessage uploadMessage;
    private final WorkClient workClient;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository, ReactiveMongoHelper reactiveMongoHelper, UploadMessage uploadMessage, WorkClient workClient) {
        this.uploadRepository = uploadRepository;
        this.reactiveMongoHelper = reactiveMongoHelper;
        this.uploadMessage = uploadMessage;
        this.workClient = workClient;
    }

    @Override
    public Mono<Upload> getUploadInfoByWorkId(String studentId, String workId) {
        return uploadRepository.findById(studentId + "|" + workId).switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业上传信息不存在", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<Void> delUploadInfoByWorkId(String studentId, String workId) {
        //TODO 作业开启状态检查
        String id = studentId + "|" + workId;
        return uploadRepository.deleteById(id).doOnSuccess(v -> uploadMessage.delOutput().send(MessageBuilder.withPayload(id).build()));
    }

    @Override
    public Mono<String> reviewWork(String studentId, String workId) {
        return reactiveMongoHelper.findOneFieldsByQuery(ImmutableMap.of("workId", workId, "studentId", studentId), Upload.class, "review")
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业上传信息不存在", HttpStatus.NOT_FOUND)))
                .map(Upload::getReview);
    }

    @Override
    public Mono<Boolean> existsById(String uploadId) {
        return uploadRepository.existsById(uploadId);
    }

    @Override
    public Mono<Upload> findOneById(String uploadId) {
        return uploadRepository.findById(uploadId);
    }

    @Override
    public Mono<Upload> save(Upload upload) {
        Upload u = upload.init();
        return uploadRepository.save(u);
    }

    @Override
    public Mono<Void> studentDropGroupFromMessage(String studentId, String groupId) {
        Upload upload = new Upload();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("size");
        Flux<Upload> uploadFlux = Flux.fromStream(workClient.getAllWorkInfoByGroupId(groupId).stream())
                .map(Work::getId)
                .flatMap(workId -> {
                    uploadMessage.delOutput().send(MessageBuilder.withPayload(studentId + "|" + workId).build());
                    Upload u = upload.clones();
                    u.setStudentId(studentId);
                    return uploadRepository.findOne(Example.of(u, matcher));
                });
        return uploadRepository.deleteAll(uploadFlux);
    }

    @Override
    public Mono<Void> teacherDelWorkFromMessage(String workId) {
        Upload upload = new Upload();
        upload.setWorkId(workId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("size");
        Flux<Upload> uploadFlux = uploadRepository.findAll(Example.of(upload, matcher))
                .map(u -> {
                    uploadMessage.delOutput().send(MessageBuilder.withPayload(u.getStudentId() + "|" + workId).build());
                    return u;
                });
        return uploadRepository.deleteAll(uploadFlux);
    }

    @Override
    public Flux<Upload> getAllUploadByWorkId(String workId) {
        Upload upload = new Upload();
        upload.setWorkId(workId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("size");
        return uploadRepository.findAll(Example.of(upload, matcher));
    }
}
