package life.xiaobao.repository;

import life.xiaobao.domain.MarkTag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarkTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkTagRepository extends JpaRepository<MarkTag, Long> {

}
