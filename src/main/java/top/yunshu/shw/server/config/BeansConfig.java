package top.yunshu.shw.server.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Beans Config
 *
 * @author itning
 */
@Component
public class BeansConfig {
    /**
     * <p>Add ModelMapper Bean</p>
     * <p>You can also see {@link ModelMapperConfig}</p>
     *
     * @return {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
