package top.yunshu.shw.server.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.group.GroupService;

/**
 * @author itning
 */
@Component
public class ModelMapperConfig {
    private final ModelMapper modelMapper;

    private final GroupService groupService;

    @Autowired
    public ModelMapperConfig(ModelMapper modelMapper, GroupService groupService) {
        this.modelMapper = modelMapper;
        this.groupService = groupService;
        addWorkMappings();
    }

    /**
     * 作业映射
     */
    private void addWorkMappings() {
        modelMapper.addMappings(new PropertyMap<Work, WorkModel>() {
            @Override
            protected void configure() {

                using(new AbstractConverter<String, String>() {
                    @Override
                    protected String convert(String source) {
                        return groupService.findGroupNameByGroupId(source);
                    }
                }).map(source.getGroupId(), destination.getGroupName());
                using(new AbstractConverter<String, String>() {
                    @Override
                    protected String convert(String source) {
                        return groupService.findTeacherNameById(source);
                    }
                }).map(source.getGroupId(), destination.getTeacherName());
            }
        });
    }
}
