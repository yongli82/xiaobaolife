package life.xiaobao.controller;

import life.xiaobao.bean.ArticleBean;
import life.xiaobao.bean.UploadResponseBean;
import life.xiaobao.domain.*;
import life.xiaobao.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @author yangyongli
 */
@Controller
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;


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

    @RequestMapping(value = "/article/edit/{uuid}")
    public String editor(@PathVariable(value = "uuid") String uuid, Model model) {
        Article prob = new Article();
        prob.setUuid(uuid);
        Article article = articleRepository.findOne(Example.of(prob));
        model.addAttribute("article", article);
        return "editor";
    }

    @RequestMapping(value = "/article/save")
    public String saveArticle(@RequestBody ArticleBean articleDto, Model model) {
        Article article = articleDto.getArticle();
        String uuid = article.getUuid();
        if (StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString();
            article.setUuid(uuid);
        }
        article = articleRepository.save(article);
        model.addAttribute("article", article);

        Category category = articleDto.getCategory();
        if (null != category) {
            Long id = category.getId();
            if (null == id || 0L == id) {
                category = categoryRepository.save(category);
            }

            articleCategoryRepository.deleteByArticleUuId(uuid);
            String code = category.getCode();
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setCategoryCode(code);
            articleCategory.setArticleUuId(uuid);
            articleCategoryRepository.save(articleCategory);
        }

        model.addAttribute("category", category);

        articleTagRepository.deleteByArticleUuId(uuid);
        List<Tag> tags = articleDto.getTags();
        if (null != tags && !tags.isEmpty()) {
            for (Tag tag : tags) {
                String name = tag.getName();
                Tag prob = new Tag();
                prob.setName(name);
                Tag existedTag = tagRepository.findOne(Example.of(prob));
                if (null == existedTag) {
                    tag.setUuid(UUID.randomUUID().toString());
                    existedTag = tagRepository.save(tag);
                }
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagUuId(existedTag.getUuid());
                articleTag.setArticleUuId(uuid);
                articleTagRepository.save(articleTag);
            }
        }

        model.addAttribute("tags", tags);

        return "article";
    }

    private static String LOCAL_UPLOAD_DIR = "/Users/yangyongli/Spring/xiaobaolife/build/www/static/upload"; //ArticleController.class.getResource("/").getPath() + "upload";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @RequestMapping(value = "/article/upload")
    @ResponseBody
    public UploadResponseBean uploadImage(@RequestParam("file") MultipartFile file) {
        UploadResponseBean bean = new UploadResponseBean();
        String fileName = file.getOriginalFilename();
        File uploadFolder = new File(LOCAL_UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            boolean result = uploadFolder.mkdirs();
            logger.debug("create dirs: [" + LOCAL_UPLOAD_DIR + "], result=" + result);
        }

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DATE_FORMAT);

        fileName = timestamp + "_" + fileName;
        File localFile = new File(uploadFolder, fileName);
        try {
            file.transferTo(localFile);
            bean.setFile_path("/static/upload/" + fileName);
        } catch (IOException e) {
            logger.error("failed", e);
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }
        //Files.write(file.getBytes(), localFile);

        return bean;
    }

}
