package life.xiaobao.web.rest;

import com.codahale.metrics.annotation.Timed;
import life.xiaobao.domain.MarkTag;

import life.xiaobao.repository.MarkTagRepository;
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
 * REST controller for managing MarkTag.
 */
@RestController
@RequestMapping("/api")
public class MarkTagResource {

    private final Logger log = LoggerFactory.getLogger(MarkTagResource.class);

    private static final String ENTITY_NAME = "markTag";

    private final MarkTagRepository markTagRepository;

    public MarkTagResource(MarkTagRepository markTagRepository) {
        this.markTagRepository = markTagRepository;
    }

    /**
     * POST  /mark-tags : Create a new markTag.
     *
     * @param markTag the markTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new markTag, or with status 400 (Bad Request) if the markTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mark-tags")
    @Timed
    public ResponseEntity<MarkTag> createMarkTag(@RequestBody MarkTag markTag) throws URISyntaxException {
        log.debug("REST request to save MarkTag : {}", markTag);
        if (markTag.getId() != null) {
            throw new BadRequestAlertException("A new markTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarkTag result = markTagRepository.save(markTag);
        return ResponseEntity.created(new URI("/api/mark-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mark-tags : Updates an existing markTag.
     *
     * @param markTag the markTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated markTag,
     * or with status 400 (Bad Request) if the markTag is not valid,
     * or with status 500 (Internal Server Error) if the markTag couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mark-tags")
    @Timed
    public ResponseEntity<MarkTag> updateMarkTag(@RequestBody MarkTag markTag) throws URISyntaxException {
        log.debug("REST request to update MarkTag : {}", markTag);
        if (markTag.getId() == null) {
            return createMarkTag(markTag);
        }
        MarkTag result = markTagRepository.save(markTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, markTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mark-tags : get all the markTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of markTags in body
     */
    @GetMapping("/mark-tags")
    @Timed
    public List<MarkTag> getAllMarkTags() {
        log.debug("REST request to get all MarkTags");
        return markTagRepository.findAll();
        }

    /**
     * GET  /mark-tags/:id : get the "id" markTag.
     *
     * @param id the id of the markTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the markTag, or with status 404 (Not Found)
     */
    @GetMapping("/mark-tags/{id}")
    @Timed
    public ResponseEntity<MarkTag> getMarkTag(@PathVariable Long id) {
        log.debug("REST request to get MarkTag : {}", id);
        MarkTag markTag = markTagRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(markTag));
    }

    /**
     * DELETE  /mark-tags/:id : delete the "id" markTag.
     *
     * @param id the id of the markTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mark-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarkTag(@PathVariable Long id) {
        log.debug("REST request to delete MarkTag : {}", id);
        markTagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
