package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.domain.ArticleTag;
import life.xiaobao.domain.Tag;
import life.xiaobao.repository.ArticleRepository;
import life.xiaobao.repository.ArticleTagRepository;
import life.xiaobao.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangyongli
 */
@Controller
public class MarkTagController {
    public static final int PAGE_SIZE = 10;
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository markTagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;


    @RequestMapping(value = "/tags")
    @ResponseBody
    public List<Tag> categories() {
        List<Tag> tags = markTagRepository.findAll();
        return tags;
    }

    @RequestMapping(value = {"/tag/{code}", "/tag/{code}/{pageNo}"})
    public String articleList(@PathVariable(value = "code") String code,
                              @PathVariable(value = "pageNo", required = false) Integer pageNo,
                              Model model) {
        if (null == pageNo) {
            pageNo = 0;
        }

        ArticleTag articleTag = new ArticleTag();
        articleTag.setTagUuId(code);
        Example<ArticleTag> example = Example.of(articleTag);
        Sort sort = new Sort(Sort.Direction.DESC, "addTime");
        Pageable pageable = new PageRequest(pageNo, 10, sort);
        Page<ArticleTag> articlesInTag = articleTagRepository.findAll(example, pageable);

        List<String> articleUUIDList = articlesInTag.getContent().stream().map(item -> item.getArticleUuId()).collect(Collectors.toList());

        List<Article> articles = articleRepository.findAllByUuidIn(articleUUIDList);
        model.addAttribute("tagCode", code);
        model.addAttribute("articles", articles);
        model.addAttribute("pageNo", articlesInTag.getNumber());
        model.addAttribute("totalPage", articlesInTag.getTotalPages());
        return "tagArticles";
    }
}
