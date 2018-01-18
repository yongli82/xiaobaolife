package life.xiaobao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A ArticleTag.
 */
@Entity
@Table(name = "article_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_uu_id")
    private String tagUuId;

    @Column(name = "article_uu_id")
    private String articleUuId;

    @Column(name = "add_time")
    private ZonedDateTime addTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagUuId() {
        return tagUuId;
    }

    public void setTagUuId(String tagUuId) {
        this.tagUuId = tagUuId;
    }

    public String getArticleUuId() {
        return articleUuId;
    }

    public void setArticleUuId(String articleUuId) {
        this.articleUuId = articleUuId;
    }

    public ZonedDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(ZonedDateTime addTime) {
        this.addTime = addTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleTag articleTag = (ArticleTag) o;
        if (articleTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articleTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticleTag{" +
            "id=" + getId() +
            ", tagUuId='" + getTagUuId() + "'" +
            ", articleUuId='" + getArticleUuId() + "'" +
            ", addTime='" + getAddTime() + "'" +
            "}";
    }
}
