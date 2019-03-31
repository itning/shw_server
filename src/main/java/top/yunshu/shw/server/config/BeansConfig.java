package top.yunshu.shw.server.config;

import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.MultipartConfigElement;

/**
 * Beans Config
 *
 * @author itning
 */
@Configuration
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(System.getProperty("java.io.tmpdir"));
        return factory.createMultipartConfig();
    }
}
