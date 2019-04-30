package top.itning.server.shwsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author itning
 * @date 2019/4/30 12:23
 */
@Configuration
public class BeansConfig {
    private final CasProperties casProperties;

    @Autowired
    public BeansConfig(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //ms
        factory.setReadTimeout(casProperties.getRequestReadTimeout());
        //ms
        factory.setConnectTimeout(casProperties.getRequestConnectTimeout());
        return new RestTemplate(factory);
    }
}
