package top.yunshu.shw.server.cas;

import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;


/**
 * @author itning
 */
@SuppressWarnings("all")
@EnableCasClient
@Component
public class CasProtectedApplication extends CasClientConfigurerAdapter {
    @Override
    public void configureValidationFilter(FilterRegistrationBean validationFilter) {
        validationFilter.getInitParameters().put("millisBetweenCleanUps", "120000");
    }

    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        authenticationFilter.getInitParameters().put("artifactParameterName", "casTicket");
        authenticationFilter.getInitParameters().put("serviceParameterName", "targetService");
    }
}