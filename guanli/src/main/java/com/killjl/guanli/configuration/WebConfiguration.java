package com.killjl.guanli.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.killjl.guanli.Interceptor.*;
import com.killjl.guanli.Interceptor.LoginInterceptor;

@Component

public class WebConfiguration extends WebMvcConfigurerAdapter{

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/user*");
        //registry.addInterceptor(new TcrPassportInterceptor()).addPathPatterns("/tcrmsg/*");
        //registry.addInterceptor(new StdPassportInterceptor()).addPathPatterns("/stdmsg/*");
        super.addInterceptors(registry);
    }
}
