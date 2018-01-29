package life.xiaobao.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.annotation.Resource;
import java.net.URL;

@Configuration
public class ThymeleafConfiguration {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(ThymeleafConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Bean("emailTemplateResolver")
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/mails/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML");
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setCacheable(false);
        emailTemplateResolver.setOrder(2);
        return emailTemplateResolver;
    }
//
//    @Bean("webTemplateResolver")
//    @Primary
//    @Description("Thymeleaf template resolver serving HTML 5 pages")
//    public SpringResourceTemplateResolver webTemplateResolver() {
//        SpringResourceTemplateResolver webTemplateResolver = new SpringResourceTemplateResolver();
//        webTemplateResolver.setPrefix("/templates/");
//        webTemplateResolver.setSuffix(".html");
//        webTemplateResolver.setTemplateMode("HTML");
//        webTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
//        webTemplateResolver.setCacheable(false);
//        webTemplateResolver.setOrder(1);
//        return webTemplateResolver;
//    }

    /**
     * 在TemplateEngine加上LayoutDialect才能使layout生效
     * 不要忘记在build.gradle中增加依赖项
     * compile group: 'nz.net.ultraq.thymeleaf', name: 'thymeleaf-layout-dialect', version: '2.2.2'
     *
     * @return
     */
//    @Bean
//    TemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addDialect(new LayoutDialect());
//        //templateEngine.addDialect(new Java8TimeDialect());
//        return templateEngine;
//    }


//    private ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        URL resource = this.getClass().getClassLoader().getResource("templates/");        //这里把系统获取到的Class的path替换为源码对应的Path，这样修改的时候就可以动态刷新
//        String devResource = resource.getFile().toString().replaceAll("target/classes", "src/main/resources");
//        resolver.setPrefix("file:" + devResource);       //不允许缓存
//        resolver.setCacheable(false);
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/classes/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(false);
        templateResolver.setOrder(1);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
}
