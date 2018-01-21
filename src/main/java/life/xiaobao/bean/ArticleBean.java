package life.xiaobao.bean;

import life.xiaobao.domain.Article;
import life.xiaobao.domain.Category;
import life.xiaobao.domain.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangyongli
 */
@Data
public class ArticleBean implements Serializable {
    private Article article;
    private Category category;
    private List<Tag> tags;
}
