package life.xiaobao.repository;

import life.xiaobao.domain.Article;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUuidIn(List<String> articleUUIDList);

    List<Article> findAllByTitleLikeOrContentLike(String key, String key1);
}
