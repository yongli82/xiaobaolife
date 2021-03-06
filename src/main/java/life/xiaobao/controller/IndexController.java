package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public String index(Model model) {
        Pageable pageable = new PageRequest(1, PAGE_SIZE);
        Page<Article> page = articleRepository.findAll(pageable);
        model.addAttribute("articles", page.getContent());
        model.addAttribute("pageNo", page.getNumber());
        model.addAttribute("totalPage", page.getTotalPages());
        return "home/home";
    }

    @RequestMapping(value = "/admin")
    public String management() {
        return "forward:/index.html";
    }

    /**
     * 静态页面，调试html用
     *
     * @param pageName
     * @return
     */
    @RequestMapping(value = "/page/{pageName}")
    public String staticPage(@PathVariable(value = "pageName", required = false) String pageName) {
        return pageName;
    }
}
