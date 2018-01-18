package life.xiaobao.web.rest;

import com.codahale.metrics.annotation.Timed;
import life.xiaobao.domain.ArticleCategory;

import life.xiaobao.repository.ArticleCategoryRepository;
import life.xiaobao.web.rest.errors.BadRequestAlertException;
import life.xiaobao.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ArticleCategory.
 */
@RestController
@RequestMapping("/api")
public class ArticleCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ArticleCategoryResource.class);

    private static final String ENTITY_NAME = "articleCategory";

    private final ArticleCategoryRepository articleCategoryRepository;

    public ArticleCategoryResource(ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryRepository = articleCategoryRepository;
    }

    /**
     * POST  /article-categories : Create a new articleCategory.
     *
     * @param articleCategory the articleCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articleCategory, or with status 400 (Bad Request) if the articleCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/article-categories")
    @Timed
    public ResponseEntity<ArticleCategory> createArticleCategory(@RequestBody ArticleCategory articleCategory) throws URISyntaxException {
        log.debug("REST request to save ArticleCategory : {}", articleCategory);
        if (articleCategory.getId() != null) {
            throw new BadRequestAlertException("A new articleCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleCategory result = articleCategoryRepository.save(articleCategory);
        return ResponseEntity.created(new URI("/api/article-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /article-categories : Updates an existing articleCategory.
     *
     * @param articleCategory the articleCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articleCategory,
     * or with status 400 (Bad Request) if the articleCategory is not valid,
     * or with status 500 (Internal Server Error) if the articleCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/article-categories")
    @Timed
    public ResponseEntity<ArticleCategory> updateArticleCategory(@RequestBody ArticleCategory articleCategory) throws URISyntaxException {
        log.debug("REST request to update ArticleCategory : {}", articleCategory);
        if (articleCategory.getId() == null) {
            return createArticleCategory(articleCategory);
        }
        ArticleCategory result = articleCategoryRepository.save(articleCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articleCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /article-categories : get all the articleCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of articleCategories in body
     */
    @GetMapping("/article-categories")
    @Timed
    public List<ArticleCategory> getAllArticleCategories() {
        log.debug("REST request to get all ArticleCategories");
        return articleCategoryRepository.findAll();
        }

    /**
     * GET  /article-categories/:id : get the "id" articleCategory.
     *
     * @param id the id of the articleCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articleCategory, or with status 404 (Not Found)
     */
    @GetMapping("/article-categories/{id}")
    @Timed
    public ResponseEntity<ArticleCategory> getArticleCategory(@PathVariable Long id) {
        log.debug("REST request to get ArticleCategory : {}", id);
        ArticleCategory articleCategory = articleCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articleCategory));
    }

    /**
     * DELETE  /article-categories/:id : delete the "id" articleCategory.
     *
     * @param id the id of the articleCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/article-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticleCategory(@PathVariable Long id) {
        log.debug("REST request to delete ArticleCategory : {}", id);
        articleCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
