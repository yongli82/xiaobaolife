package life.xiaobao.web.rest;

import life.xiaobao.XiaobaolifeApp;

import life.xiaobao.domain.ArticleTag;
import life.xiaobao.repository.ArticleTagRepository;
import life.xiaobao.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static life.xiaobao.web.rest.TestUtil.sameInstant;
import static life.xiaobao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleTagResource REST controller.
 *
 * @see ArticleTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaobaolifeApp.class)
public class ArticleTagResourceIntTest {

    private static final String DEFAULT_TAG_UU_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAG_UU_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_UU_ID = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_UU_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ADD_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleTagMockMvc;

    private ArticleTag articleTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleTagResource articleTagResource = new ArticleTagResource(articleTagRepository);
        this.restArticleTagMockMvc = MockMvcBuilders.standaloneSetup(articleTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleTag createEntity(EntityManager em) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setTagUuId(DEFAULT_TAG_UU_ID);
        articleTag.setArticleUuId(DEFAULT_ARTICLE_UU_ID);
        articleTag.setAddTime(DEFAULT_ADD_TIME);
        return articleTag;
    }

    @Before
    public void initTest() {
        articleTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleTag() throws Exception {
        int databaseSizeBeforeCreate = articleTagRepository.findAll().size();

        // Create the ArticleTag
        restArticleTagMockMvc.perform(post("/api/article-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleTag)))
            .andExpect(status().isCreated());

        // Validate the ArticleTag in the database
        List<ArticleTag> articleTagList = articleTagRepository.findAll();
        assertThat(articleTagList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleTag testArticleTag = articleTagList.get(articleTagList.size() - 1);
        assertThat(testArticleTag.getTagUuId()).isEqualTo(DEFAULT_TAG_UU_ID);
        assertThat(testArticleTag.getArticleUuId()).isEqualTo(DEFAULT_ARTICLE_UU_ID);
        assertThat(testArticleTag.getAddTime()).isEqualTo(DEFAULT_ADD_TIME);
    }

    @Test
    @Transactional
    public void createArticleTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleTagRepository.findAll().size();

        // Create the ArticleTag with an existing ID
        articleTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleTagMockMvc.perform(post("/api/article-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleTag)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleTag in the database
        List<ArticleTag> articleTagList = articleTagRepository.findAll();
        assertThat(articleTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticleTags() throws Exception {
        // Initialize the database
        articleTagRepository.saveAndFlush(articleTag);

        // Get all the articleTagList
        restArticleTagMockMvc.perform(get("/api/article-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tagUuId").value(hasItem(DEFAULT_TAG_UU_ID.toString())))
            .andExpect(jsonPath("$.[*].articleUuId").value(hasItem(DEFAULT_ARTICLE_UU_ID.toString())))
            .andExpect(jsonPath("$.[*].addTime").value(hasItem(sameInstant(DEFAULT_ADD_TIME))));
    }

    @Test
    @Transactional
    public void getArticleTag() throws Exception {
        // Initialize the database
        articleTagRepository.saveAndFlush(articleTag);

        // Get the articleTag
        restArticleTagMockMvc.perform(get("/api/article-tags/{id}", articleTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articleTag.getId().intValue()))
            .andExpect(jsonPath("$.tagUuId").value(DEFAULT_TAG_UU_ID.toString()))
            .andExpect(jsonPath("$.articleUuId").value(DEFAULT_ARTICLE_UU_ID.toString()))
            .andExpect(jsonPath("$.addTime").value(sameInstant(DEFAULT_ADD_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingArticleTag() throws Exception {
        // Get the articleTag
        restArticleTagMockMvc.perform(get("/api/article-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleTag() throws Exception {
        // Initialize the database
        articleTagRepository.saveAndFlush(articleTag);
        int databaseSizeBeforeUpdate = articleTagRepository.findAll().size();

        // Update the articleTag
        ArticleTag updatedArticleTag = articleTagRepository.findOne(articleTag.getId());
        // Disconnect from session so that the updates on updatedArticleTag are not directly saved in db
        em.detach(updatedArticleTag);
        updatedArticleTag.setTagUuId(UPDATED_TAG_UU_ID);
        updatedArticleTag.setArticleUuId(UPDATED_ARTICLE_UU_ID);
        updatedArticleTag.setAddTime(UPDATED_ADD_TIME);

        restArticleTagMockMvc.perform(put("/api/article-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticleTag)))
            .andExpect(status().isOk());

        // Validate the ArticleTag in the database
        List<ArticleTag> articleTagList = articleTagRepository.findAll();
        assertThat(articleTagList).hasSize(databaseSizeBeforeUpdate);
        ArticleTag testArticleTag = articleTagList.get(articleTagList.size() - 1);
        assertThat(testArticleTag.getTagUuId()).isEqualTo(UPDATED_TAG_UU_ID);
        assertThat(testArticleTag.getArticleUuId()).isEqualTo(UPDATED_ARTICLE_UU_ID);
        assertThat(testArticleTag.getAddTime()).isEqualTo(UPDATED_ADD_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleTag() throws Exception {
        int databaseSizeBeforeUpdate = articleTagRepository.findAll().size();

        // Create the ArticleTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleTagMockMvc.perform(put("/api/article-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleTag)))
            .andExpect(status().isCreated());

        // Validate the ArticleTag in the database
        List<ArticleTag> articleTagList = articleTagRepository.findAll();
        assertThat(articleTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticleTag() throws Exception {
        // Initialize the database
        articleTagRepository.saveAndFlush(articleTag);
        int databaseSizeBeforeDelete = articleTagRepository.findAll().size();

        // Get the articleTag
        restArticleTagMockMvc.perform(delete("/api/article-tags/{id}", articleTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticleTag> articleTagList = articleTagRepository.findAll();
        assertThat(articleTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleTag.class);
        ArticleTag articleTag1 = new ArticleTag();
        articleTag1.setId(1L);
        ArticleTag articleTag2 = new ArticleTag();
        articleTag2.setId(articleTag1.getId());
        assertThat(articleTag1).isEqualTo(articleTag2);
        articleTag2.setId(2L);
        assertThat(articleTag1).isNotEqualTo(articleTag2);
        articleTag1.setId(null);
        assertThat(articleTag1).isNotEqualTo(articleTag2);
    }
}
