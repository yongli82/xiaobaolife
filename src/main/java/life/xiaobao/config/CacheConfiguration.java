package life.xiaobao.config;

import io.github.jhipster.config.JHipsterProperties;
import life.xiaobao.domain.Tag;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.Article.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.ArticleCategory.class.getName(), jcacheConfiguration);
            cm.createCache(life.xiaobao.domain.ArticleTag.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
