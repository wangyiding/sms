package org.edingsoft.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * ϵͳ����
 * @author bob
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages={"org.edingsoft"})
public class WebConfig extends WebMvcConfigurerAdapter {
	
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public AnnotationMethodHandlerAdapter JsonParser(){
    	AnnotationMethodHandlerAdapter ano=new AnnotationMethodHandlerAdapter();
    	MappingJackson2HttpMessageConverter cvt=new MappingJackson2HttpMessageConverter();
    	MappingJackson2HttpMessageConverter[] cvts={cvt};
    	ano.setMessageConverters(cvts);
    	return ano;
    }

}