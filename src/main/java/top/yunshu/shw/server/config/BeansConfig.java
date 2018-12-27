package top.yunshu.shw.server.config;

import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.yunshu.shw.server.cas.AutoSetUserAdapterFilter;

/**
 * Beans Config
 *
 * @author itning
 */
@SuppressWarnings("all")
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
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("*");
        registration.setName("CorsFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        registration.addUrlPatterns("*");
        registration.setName("httpServletRequest");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean assertionThreadLocalFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AssertionThreadLocalFilter());
        registration.addUrlPatterns("*");
        registration.setName("httpServletRequest");
        registration.setOrder(3);

        return registration;
    }


    @Bean
    public FilterRegistrationBean autoSetHRUserAdapterFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new AutoSetUserAdapterFilter());
        registration.addUrlPatterns("*");
        registration.setName("adapterFilter");
        registration.setOrder(4);


        return registration;
    }

    @Bean(name = "autoSetHRUserAdapterFilter")
    public AutoSetUserAdapterFilter systemUrlFilter() {
        return new AutoSetUserAdapterFilter();
    }
}
