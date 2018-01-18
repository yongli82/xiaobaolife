package life.xiaobao.web.rest;

import life.xiaobao.XiaobaolifeApp;

import life.xiaobao.domain.ArticleCategory;
import life.xiaobao.repository.ArticleCategoryRepository;
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
 * Test class for the ArticleCategoryResource REST controller.
 *
 * @see ArticleCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaobaolifeApp.class)
public class ArticleCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_UU_ID = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_UU_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ADD_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleCategoryMockMvc;

    private ArticleCategory articleCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticleCategoryResource articleCategoryResource = new ArticleCategoryResource(articleCategoryRepository);
        this.restArticleCategoryMockMvc = MockMvcBuilders.standaloneSetup(articleCategoryResource)
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
    public static ArticleCategory createEntity(EntityManager em) {
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategoryCode(DEFAULT_CATEGORY_CODE);
        articleCategory.setArticleUuId(DEFAULT_ARTICLE_UU_ID);
        articleCategory.setAddTime(DEFAULT_ADD_TIME);
        return articleCategory;
    }

    @Before
    public void initTest() {
        articleCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleCategory() throws Exception {
        int databaseSizeBeforeCreate = articleCategoryRepository.findAll().size();

        // Create the ArticleCategory
        restArticleCategoryMockMvc.perform(post("/api/article-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleCategory)))
            .andExpect(status().isCreated());

        // Validate the ArticleCategory in the database
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();
        assertThat(articleCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleCategory testArticleCategory = articleCategoryList.get(articleCategoryList.size() - 1);
        assertThat(testArticleCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testArticleCategory.getArticleUuId()).isEqualTo(DEFAULT_ARTICLE_UU_ID);
        assertThat(testArticleCategory.getAddTime()).isEqualTo(DEFAULT_ADD_TIME);
    }

    @Test
    @Transactional
    public void createArticleCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleCategoryRepository.findAll().size();

        // Create the ArticleCategory with an existing ID
        articleCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleCategoryMockMvc.perform(post("/api/article-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleCategory in the database
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();
        assertThat(articleCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticleCategories() throws Exception {
        // Initialize the database
        articleCategoryRepository.saveAndFlush(articleCategory);

        // Get all the articleCategoryList
        restArticleCategoryMockMvc.perform(get("/api/article-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE.toString())))
            .andExpect(jsonPath("$.[*].articleUuId").value(hasItem(DEFAULT_ARTICLE_UU_ID.toString())))
            .andExpect(jsonPath("$.[*].addTime").value(hasItem(sameInstant(DEFAULT_ADD_TIME))));
    }

    @Test
    @Transactional
    public void getArticleCategory() throws Exception {
        // Initialize the database
        articleCategoryRepository.saveAndFlush(articleCategory);

        // Get the articleCategory
        restArticleCategoryMockMvc.perform(get("/api/article-categories/{id}", articleCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articleCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.articleUuId").value(DEFAULT_ARTICLE_UU_ID.toString()))
            .andExpect(jsonPath("$.addTime").value(sameInstant(DEFAULT_ADD_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingArticleCategory() throws Exception {
        // Get the articleCategory
        restArticleCategoryMockMvc.perform(get("/api/article-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleCategory() throws Exception {
        // Initialize the database
        articleCategoryRepository.saveAndFlush(articleCategory);
        int databaseSizeBeforeUpdate = articleCategoryRepository.findAll().size();

        // Update the articleCategory
        ArticleCategory updatedArticleCategory = articleCategoryRepository.findOne(articleCategory.getId());
        // Disconnect from session so that the updates on updatedArticleCategory are not directly saved in db
        em.detach(updatedArticleCategory);
        updatedArticleCategory.setCategoryCode(UPDATED_CATEGORY_CODE);
        updatedArticleCategory.setArticleUuId(UPDATED_ARTICLE_UU_ID);
        updatedArticleCategory.setAddTime(UPDATED_ADD_TIME);

        restArticleCategoryMockMvc.perform(put("/api/article-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticleCategory)))
            .andExpect(status().isOk());

        // Validate the ArticleCategory in the database
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();
        assertThat(articleCategoryList).hasSize(databaseSizeBeforeUpdate);
        ArticleCategory testArticleCategory = articleCategoryList.get(articleCategoryList.size() - 1);
        assertThat(testArticleCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testArticleCategory.getArticleUuId()).isEqualTo(UPDATED_ARTICLE_UU_ID);
        assertThat(testArticleCategory.getAddTime()).isEqualTo(UPDATED_ADD_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleCategory() throws Exception {
        int databaseSizeBeforeUpdate = articleCategoryRepository.findAll().size();

        // Create the ArticleCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleCategoryMockMvc.perform(put("/api/article-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articleCategory)))
            .andExpect(status().isCreated());

        // Validate the ArticleCategory in the database
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();
        assertThat(articleCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticleCategory() throws Exception {
        // Initialize the database
        articleCategoryRepository.saveAndFlush(articleCategory);
        int databaseSizeBeforeDelete = articleCategoryRepository.findAll().size();

        // Get the articleCategory
        restArticleCategoryMockMvc.perform(delete("/api/article-categories/{id}", articleCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findAll();
        assertThat(articleCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleCategory.class);
        ArticleCategory articleCategory1 = new ArticleCategory();
        articleCategory1.setId(1L);
        ArticleCategory articleCategory2 = new ArticleCategory();
        articleCategory2.setId(articleCategory1.getId());
        assertThat(articleCategory1).isEqualTo(articleCategory2);
        articleCategory2.setId(2L);
        assertThat(articleCategory1).isNotEqualTo(articleCategory2);
        articleCategory1.setId(null);
        assertThat(articleCategory1).isNotEqualTo(articleCategory2);
    }
}
