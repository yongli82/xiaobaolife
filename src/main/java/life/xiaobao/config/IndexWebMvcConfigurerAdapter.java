package life.xiaobao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yangyongli
 */
@Configuration
public class IndexWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:/articles/0");
//        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
//        super.addViewControllers(registry);
//    }
}
