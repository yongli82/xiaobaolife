package life.xiaobao.repository;

import life.xiaobao.domain.ArticleCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArticleCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {

}
