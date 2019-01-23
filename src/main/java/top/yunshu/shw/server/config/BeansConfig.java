package top.yunshu.shw.server.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    @Bean
    public FilterRegistrationBean adminFilterRegistration() {
        FilterRegistrationBean<AdminFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AdminFilter());
        registration.addUrlPatterns("/config/actuator/*");
        registration.setName("adminFilter");
        registration.setOrder(1);
        return registration;
    }
}
