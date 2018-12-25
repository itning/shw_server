package top.yunshu.shw.server.cas;

import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author itning
 */
@SuppressWarnings("all")
public class CasBeans {
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        registration.addUrlPatterns("*");
        registration.setName("httpServletRequest");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean assertionThreadLocalFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AssertionThreadLocalFilter());
        registration.addUrlPatterns("*");
        registration.setName("httpServletRequest");
        registration.setOrder(2);

        return registration;
    }


    @Bean
    public FilterRegistrationBean autoSetHRUserAdapterFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new AutoSetUserAdapterFilter());
        registration.addUrlPatterns("*");
        registration.setName("adapterFilter");
        registration.setOrder(3);


        return registration;
    }


    @Bean
    public AutoSetUserAdapterFilter autoSetUserAdapterFilter() {
        return new AutoSetUserAdapterFilter();
    }

    @Bean(name = "autoSetHRUserAdapterFilter")
    public AutoSetUserAdapterFilter systemUrlFilter() {
        return new AutoSetUserAdapterFilter();
    }
}
