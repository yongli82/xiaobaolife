package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangyongli
 */
@Controller
public class IndexController {

    public static final int PAGE_SIZE = 10;
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        return articleList(0, model);
    }

    @RequestMapping(value = "/articles/{pageNo}")
    public String articleList(@PathVariable(value = "pageNo", required = false) Integer pageNo, Model model) {
        if (null == pageNo) {
            pageNo = 0;
        }
        Pageable pageable = new PageRequest(pageNo, 10);
        Page<Article> page = articleRepository.findAll(pageable);
        model.addAttribute("articles", page.getContent());
        model.addAttribute("pageNo", page.getNumber());
        model.addAttribute("totalPage", page.getTotalPages());
        return "articles";
    }

    @RequestMapping(value = "/article/{uuid}")
    public String home(@PathVariable(value = "uuid") String uuid, Model model) {
        Article prob = new Article();
        prob.setUuid(uuid);
        Article article = articleRepository.findOne(Example.of(prob));
        model.addAttribute("article", article);
        return "article";
    }

    @RequestMapping(value = "/admin")
    public String management() {
        return "forward:/index.html";
    }
}
