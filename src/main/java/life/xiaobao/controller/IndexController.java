package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangyongli
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        Pageable pageable = new PageRequest(0, 10);
        Page<Article> page = articleRepository.findAll(pageable);
        model.addAttribute("articles", page.getContent());
        model.addAttribute("page", page.getNumber());
        model.addAttribute("page", page.getTotalPages());
        return "home";
    }

    @RequestMapping(value = "/home")
    public String home(Model model) {
        Pageable pageable = new PageRequest(0, 10);
        Page<Article> page = articleRepository.findAll(pageable);
        model.addAttribute("articles", page.getContent());
        model.addAttribute("page", page.getNumber());
        model.addAttribute("page", page.getTotalPages());
        return "home";
    }

    @RequestMapping(value = "/admin")
    public String management() {
        return "forward:/index.html";
    }
}
