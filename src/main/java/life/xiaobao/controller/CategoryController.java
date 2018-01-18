package life.xiaobao.controller;

import life.xiaobao.domain.Article;
import life.xiaobao.domain.ArticleCategory;
import life.xiaobao.domain.Category;
import life.xiaobao.repository.ArticleCategoryRepository;
import life.xiaobao.repository.ArticleRepository;
import life.xiaobao.repository.CategoryRepository;
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
public class CategoryController {

    public static final int PAGE_SIZE = 10;
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;


    @RequestMapping(value = "/categories")
    @ResponseBody
    public List<Category> categories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @RequestMapping(value = {"/category/{code}", "/category/{code}/{pageNo}"})
    public String articleList(@PathVariable(value = "code") String code,
                              @PathVariable(value = "pageNo", required = false) Integer pageNo,
                              Model model) {
        if (null == pageNo) {
            pageNo = 0;
        }

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategoryCode(code);
        Example<ArticleCategory> example = Example.of(articleCategory);
        Sort sort = new Sort(Sort.Direction.DESC, "addTime");
        Pageable pageable = new PageRequest(pageNo, 10, sort);
        Page<ArticleCategory> articleCategories = articleCategoryRepository.findAll(example, pageable);

        List<String> articleUUIDList = articleCategories.getContent().stream().map(item -> item.getArticleUuId()).collect(Collectors.toList());

        List<Article> articles = articleRepository.findAllByUuidIn(articleUUIDList);
        model.addAttribute("categoryCode", code);
        model.addAttribute("articles", articles);
        model.addAttribute("pageNo", articleCategories.getNumber());
        model.addAttribute("totalPage", articleCategories.getTotalPages());
        return "categoryArticles";
    }


}
