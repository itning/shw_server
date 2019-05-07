package top.itning.server.shwwork.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.itning.server.shwwork.client.GroupClient;
import top.itning.server.shwwork.dto.WorkDTO;
import top.itning.server.shwwork.entity.Work;

/**
 * ModelMapper Config
 *
 * @author itning
 */
@Component
public class ModelMapperConfig {
    private final ModelMapper modelMapper;

    private final GroupClient groupClient;

    @Autowired
    public ModelMapperConfig(ModelMapper modelMapper, GroupClient groupClient) {
        this.modelMapper = modelMapper;
        this.groupClient = groupClient;
        addWorkMappings();
    }

    /**
     * 作业映射
     */
    private void addWorkMappings() {
        modelMapper.addMappings(new PropertyMap<Work, WorkDTO>() {
            @Override
            protected void configure() {
                using(new AbstractConverter<String, String>() {
                    @Override
                    protected String convert(String source) {
                        return groupClient.findGroupNameByGroupId(source);
                    }
                }).map(source.getGroupId(), destination.getGroupName());

                using(new AbstractConverter<String, String>() {
                    @Override
                    protected String convert(String source) {
                        return groupClient.findTeacherNameById(source);
                    }
                }).map(source.getGroupId(), destination.getTeacherName());
            }
        });
    }
}
