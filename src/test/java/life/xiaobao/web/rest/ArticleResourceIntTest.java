package life.xiaobao.web.rest;

import life.xiaobao.XiaobaolifeApp;

import life.xiaobao.domain.Article;
import life.xiaobao.repository.ArticleRepository;
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

import life.xiaobao.domain.enumeration.RecordStatus;
/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaobaolifeApp.class)
public class ArticleResourceIntTest {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_IMAGE_BIG = "AAAAAAAAAA";
    private static final String UPDATED_COVER_IMAGE_BIG = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_IMAGE_SMALL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_IMAGE_SMALL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUBLISH_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLISH_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_RECORD_STATUS = RecordStatus.VALID;
    private static final RecordStatus UPDATED_RECORD_STATUS = RecordStatus.INVALID;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleMockMvc;

    private Article article;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleResource articleResource = new ArticleResource(articleRepository);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
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
    public static Article createEntity(EntityManager em) {
        Article article = new Article();
        article.setUuid(DEFAULT_UUID);
        article.setTitle(DEFAULT_TITLE);
        article.setSubTitle(DEFAULT_SUB_TITLE);
        article.setAuthorName(DEFAULT_AUTHOR_NAME);
        article.setIntroduction(DEFAULT_INTRODUCTION);
        article.setCoverImageBig(DEFAULT_COVER_IMAGE_BIG);
        article.setCoverImageSmall(DEFAULT_COVER_IMAGE_SMALL);
        article.setContent(DEFAULT_CONTENT);
        article.setPublishTime(DEFAULT_PUBLISH_TIME);
        article.setMemo(DEFAULT_MEMO);
        article.setRecordStatus(DEFAULT_RECORD_STATUS);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticle.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testArticle.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testArticle.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testArticle.getCoverImageBig()).isEqualTo(DEFAULT_COVER_IMAGE_BIG);
        assertThat(testArticle.getCoverImageSmall()).isEqualTo(DEFAULT_COVER_IMAGE_SMALL);
        assertThat(testArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArticle.getPublishTime()).isEqualTo(DEFAULT_PUBLISH_TIME);
        assertThat(testArticle.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testArticle.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].coverImageBig").value(hasItem(DEFAULT_COVER_IMAGE_BIG.toString())))
            .andExpect(jsonPath("$.[*].coverImageSmall").value(hasItem(DEFAULT_COVER_IMAGE_SMALL.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].publishTime").value(hasItem(sameInstant(DEFAULT_PUBLISH_TIME))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.coverImageBig").value(DEFAULT_COVER_IMAGE_BIG.toString()))
            .andExpect(jsonPath("$.coverImageSmall").value(DEFAULT_COVER_IMAGE_SMALL.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.publishTime").value(sameInstant(DEFAULT_PUBLISH_TIME)))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findOne(article.getId());
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle.setUuid(UPDATED_UUID);
        updatedArticle.setTitle(UPDATED_TITLE);
        updatedArticle.setSubTitle(UPDATED_SUB_TITLE);
        updatedArticle.setAuthorName(UPDATED_AUTHOR_NAME);
        updatedArticle.setIntroduction(UPDATED_INTRODUCTION);
        updatedArticle.setCoverImageBig(UPDATED_COVER_IMAGE_BIG);
        updatedArticle.setCoverImageSmall(UPDATED_COVER_IMAGE_SMALL);
        updatedArticle.setContent(UPDATED_CONTENT);
        updatedArticle.setPublishTime(UPDATED_PUBLISH_TIME);
        updatedArticle.setMemo(UPDATED_MEMO);
        updatedArticle.setRecordStatus(UPDATED_RECORD_STATUS);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticle.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testArticle.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testArticle.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testArticle.getCoverImageBig()).isEqualTo(UPDATED_COVER_IMAGE_BIG);
        assertThat(testArticle.getCoverImageSmall()).isEqualTo(UPDATED_COVER_IMAGE_SMALL);
        assertThat(testArticle.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArticle.getPublishTime()).isEqualTo(UPDATED_PUBLISH_TIME);
        assertThat(testArticle.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testArticle.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);
        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = new Article();
        article1.setId(1L);
        Article article2 = new Article();
        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);
        article2.setId(2L);
        assertThat(article1).isNotEqualTo(article2);
        article1.setId(null);
        assertThat(article1).isNotEqualTo(article2);
    }
}
