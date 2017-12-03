package life.xiaobao.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(ThymeleafConfiguration.class);

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("mails/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML");
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setOrder(2);
        return emailTemplateResolver;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 pages")
    public ClassLoaderTemplateResolver webTemplateResolver() {
        ClassLoaderTemplateResolver webTemplateResolver = new ClassLoaderTemplateResolver();
        webTemplateResolver.setPrefix("templates/");
        webTemplateResolver.setSuffix(".html");
        webTemplateResolver.setTemplateMode("HTML");
        webTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        webTemplateResolver.setOrder(1);
        return webTemplateResolver;
    }

    /**
     * 在TemplateEngine加上LayoutDialect才能使layout生效
     * 不要忘记在build.gradle中增加依赖项
     * compile group: 'nz.net.ultraq.thymeleaf', name: 'thymeleaf-layout-dialect', version: '2.2.2'
     * @return
     */
    @Bean
    TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }
}
