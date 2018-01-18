package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.repository.ArticleRepository;
import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yangyongli
 */
@Controller
public class SearchController {
    public static final int PAGE_SIZE = 10;

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = {"/search"})
    public String articleList(@RequestParam(value = "key") String key,
                              Model model) {
        if (StringUtils.isBlank(key)) {
            return "redirect:/";
        }

        key = key.trim();
        String escapeSql = StringEscapeUtils.escapeSql(key);

        String searchKey = "%" + escapeSql + "%";
        List<Article> articles = articleRepository.findAllByTitleLikeOrContentLike(searchKey, searchKey);
        model.addAttribute("key", key);
        model.addAttribute("articles", articles);
        return "searchResult";
    }
}
