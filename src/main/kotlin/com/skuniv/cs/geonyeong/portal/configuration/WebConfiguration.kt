package com.skuniv.cs.geonyeong.portal.configuration

import com.skuniv.cs.geonyeong.portal.interceptor.PortalInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@EnableWebMvc
class WebConfiguration(
        val portalInterceptor: PortalInterceptor
) : WebMvcConfigurer {

    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("skuniv portal")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.skuniv.cs.geonyeong.portal.controller"))
                .paths(PathSelectors.ant("/api/v1/portal/**"))
                .build()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(portalInterceptor)
                .addPathPatterns("/api/v1/portal/professor/**")
                .excludePathPatterns("/api/v1/portal/account/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}