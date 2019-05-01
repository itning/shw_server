package top.itning.server.shwwork.service.impl;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.shwwork.client.StudentGroupClient;
import top.itning.server.shwwork.dto.WorkDTO;
import top.itning.server.shwwork.entity.Work;
import top.itning.server.shwwork.repository.WorkRepository;
import top.itning.server.shwwork.service.WorkService;
import top.itning.server.shwwork.util.ReactiveMongoHelper;

import java.util.Comparator;
import java.util.List;

/**
 * @author itning
 * @date 2019/5/1 9:49
 */
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final StudentGroupClient studentGroupClient;
    private final ReactiveMongoHelper reactiveMongoHelper;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, StudentGroupClient studentGroupClient, ReactiveMongoHelper reactiveMongoHelper) {
        this.workRepository = workRepository;
        this.studentGroupClient = studentGroupClient;
        this.reactiveMongoHelper = reactiveMongoHelper;
    }

    @Override
    public Mono<Page<WorkDTO>> getStudentUnDoneWork(String studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        w.setEnabled(true);
        return Flux.fromStream(studentGroupClient.findGroupIdByStudentNumber(studentId).stream())
                .flatMap(groupId -> {
                    Work work = w.clones();
                    work.setGroupId(groupId);
                    return workRepository.findAll(Example.of(work));
                })
                //TODO filter un done work in upload collection
                .sort(Comparator.comparing(Work::getGmtCreate).reversed())
                .collectList()
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }

    @Override
    public Mono<Page<WorkDTO>> getStudentDoneWork(String studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        return Flux.fromStream(studentGroupClient.findGroupIdByStudentNumber(studentId).stream())
                .flatMap(groupId -> {
                    Work work = w.clones();
                    work.setGroupId(groupId);
                    return workRepository.findAll(Example.of(work, matcher));
                })
                //TODO check upload collection exist
                //TODO setGmtCreate setGmtModified in work
                .sort(Comparator.comparing(Work::getGmtCreate).reversed())
                .collectList()
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }
}
