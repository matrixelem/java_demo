package com.example.java_demo.multi_thread.threadLocal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;


@Configuration
public class WebConfiguration {

    @Value("${facade.web.filter.urlPatterns:/*}")
    private String webPrefix;

    @Bean("webFilter")
    public Filter webFilter() {
        return new WebFilter();
    }

    @Bean
    public FilterRegistrationBean webFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("webFilter"));
        registration.setName("webFilter");
        registration.addUrlPatterns(webPrefix);
        registration.setOrder(5);
        return registration;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
