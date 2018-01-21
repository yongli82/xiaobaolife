package life.xiaobao.repository;

import life.xiaobao.domain.ArticleTag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArticleTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    int deleteByArticleUuId(String uuid);
}
