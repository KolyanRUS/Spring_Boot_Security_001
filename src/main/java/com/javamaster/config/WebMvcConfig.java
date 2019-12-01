package com.javamaster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/jquery/**").addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.4.1/");
        registry.addResourceHandler("/popper/**").addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.15.0/umd/");
        registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.3.1/");
    }
    /*@Override
    public void configurePathMatch(PathMatchConfigurer p) {
        //
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer var1) {
        //
    }
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer var1) {
        //
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer var1) {
        //
    }
    @Override
    public void addFormatters(FormatterRegistry var1) {
        //
    }
    @Override
    public void addInterceptors(InterceptorRegistry var1) {
        //
    }
    @Override
    public void addCorsMappings(CorsRegistry var1) {
        //
    }
    @Override
    public void addViewControllers(ViewControllerRegistry var1) {
        //
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry var1) {
        //
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> var1) {
        //
    }
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> var1) {
        //
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> var1) {
        //
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> var1) {
        //
    }
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> var1) {
        //
    }
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> var1) {
        //
    }
    @Override
    public Validator getValidator() {
        return null;//
    }
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;//
    }*/
}