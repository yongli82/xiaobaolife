package life.xiaobao.web.rest;

import com.codahale.metrics.annotation.Timed;
import life.xiaobao.domain.ArticleTag;

import life.xiaobao.repository.ArticleTagRepository;
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
 * REST controller for managing ArticleTag.
 */
@RestController
@RequestMapping("/api")
public class ArticleTagResource {

    private final Logger log = LoggerFactory.getLogger(ArticleTagResource.class);

    private static final String ENTITY_NAME = "articleTag";

    private final ArticleTagRepository articleTagRepository;

    public ArticleTagResource(ArticleTagRepository articleTagRepository) {
        this.articleTagRepository = articleTagRepository;
    }

    /**
     * POST  /article-tags : Create a new articleTag.
     *
     * @param articleTag the articleTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articleTag, or with status 400 (Bad Request) if the articleTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/article-tags")
    @Timed
    public ResponseEntity<ArticleTag> createArticleTag(@RequestBody ArticleTag articleTag) throws URISyntaxException {
        log.debug("REST request to save ArticleTag : {}", articleTag);
        if (articleTag.getId() != null) {
            throw new BadRequestAlertException("A new articleTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleTag result = articleTagRepository.save(articleTag);
        return ResponseEntity.created(new URI("/api/article-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /article-tags : Updates an existing articleTag.
     *
     * @param articleTag the articleTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articleTag,
     * or with status 400 (Bad Request) if the articleTag is not valid,
     * or with status 500 (Internal Server Error) if the articleTag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/article-tags")
    @Timed
    public ResponseEntity<ArticleTag> updateArticleTag(@RequestBody ArticleTag articleTag) throws URISyntaxException {
        log.debug("REST request to update ArticleTag : {}", articleTag);
        if (articleTag.getId() == null) {
            return createArticleTag(articleTag);
        }
        ArticleTag result = articleTagRepository.save(articleTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articleTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /article-tags : get all the articleTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of articleTags in body
     */
    @GetMapping("/article-tags")
    @Timed
    public List<ArticleTag> getAllArticleTags() {
        log.debug("REST request to get all ArticleTags");
        return articleTagRepository.findAll();
        }

    /**
     * GET  /article-tags/:id : get the "id" articleTag.
     *
     * @param id the id of the articleTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articleTag, or with status 404 (Not Found)
     */
    @GetMapping("/article-tags/{id}")
    @Timed
    public ResponseEntity<ArticleTag> getArticleTag(@PathVariable Long id) {
        log.debug("REST request to get ArticleTag : {}", id);
        ArticleTag articleTag = articleTagRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articleTag));
    }

    /**
     * DELETE  /article-tags/:id : delete the "id" articleTag.
     *
     * @param id the id of the articleTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/article-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticleTag(@PathVariable Long id) {
        log.debug("REST request to delete ArticleTag : {}", id);
        articleTagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
