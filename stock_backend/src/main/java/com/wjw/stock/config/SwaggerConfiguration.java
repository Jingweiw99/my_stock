package com.wjw.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket() {
        //构建在线API概要对象
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.wjw.stock.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo() {
        //网站联系方式 项目名称，url，邮箱
        Contact contact = new Contact("wjw_my_stock", "http://localhost:9999", "wangjingwei0@foxmail.com");
        return new ApiInfoBuilder()
                .title("我的指数-在线接口API文档")//文档标题
                .description("这是一个方便前后端开发人员快速了解开发接口需求的在线接口API文档")//文档描述信息
                .contact(contact)//站点联系人相关信息
                .version("1.0.0")//文档版本
                .build();
    }
}
