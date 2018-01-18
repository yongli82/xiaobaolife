package life.xiaobao.web.rest;

import life.xiaobao.XiaobaolifeApp;

import life.xiaobao.domain.MarkTag;
import life.xiaobao.repository.MarkTagRepository;
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
import java.util.List;

import static life.xiaobao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import life.xiaobao.domain.enumeration.RecordStatus;
/**
 * Test class for the MarkTagResource REST controller.
 *
 * @see MarkTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaobaolifeApp.class)
public class MarkTagResourceIntTest {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_RECORD_STATUS = RecordStatus.VALID;
    private static final RecordStatus UPDATED_RECORD_STATUS = RecordStatus.INVALID;

    @Autowired
    private MarkTagRepository markTagRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarkTagMockMvc;

    private MarkTag markTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkTagResource markTagResource = new MarkTagResource(markTagRepository);
        this.restMarkTagMockMvc = MockMvcBuilders.standaloneSetup(markTagResource)
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
    public static MarkTag createEntity(EntityManager em) {
        MarkTag markTag = new MarkTag();
        markTag.setUuid(DEFAULT_UUID);
        markTag.setName(DEFAULT_NAME);
        markTag.setRecordStatus(DEFAULT_RECORD_STATUS);
        return markTag;
    }

    @Before
    public void initTest() {
        markTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarkTag() throws Exception {
        int databaseSizeBeforeCreate = markTagRepository.findAll().size();

        // Create the MarkTag
        restMarkTagMockMvc.perform(post("/api/mark-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTag)))
            .andExpect(status().isCreated());

        // Validate the MarkTag in the database
        List<MarkTag> markTagList = markTagRepository.findAll();
        assertThat(markTagList).hasSize(databaseSizeBeforeCreate + 1);
        MarkTag testMarkTag = markTagList.get(markTagList.size() - 1);
        assertThat(testMarkTag.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testMarkTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarkTag.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    public void createMarkTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markTagRepository.findAll().size();

        // Create the MarkTag with an existing ID
        markTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkTagMockMvc.perform(post("/api/mark-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTag)))
            .andExpect(status().isBadRequest());

        // Validate the MarkTag in the database
        List<MarkTag> markTagList = markTagRepository.findAll();
        assertThat(markTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarkTags() throws Exception {
        // Initialize the database
        markTagRepository.saveAndFlush(markTag);

        // Get all the markTagList
        restMarkTagMockMvc.perform(get("/api/mark-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getMarkTag() throws Exception {
        // Initialize the database
        markTagRepository.saveAndFlush(markTag);

        // Get the markTag
        restMarkTagMockMvc.perform(get("/api/mark-tags/{id}", markTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(markTag.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarkTag() throws Exception {
        // Get the markTag
        restMarkTagMockMvc.perform(get("/api/mark-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarkTag() throws Exception {
        // Initialize the database
        markTagRepository.saveAndFlush(markTag);
        int databaseSizeBeforeUpdate = markTagRepository.findAll().size();

        // Update the markTag
        MarkTag updatedMarkTag = markTagRepository.findOne(markTag.getId());
        // Disconnect from session so that the updates on updatedMarkTag are not directly saved in db
        em.detach(updatedMarkTag);
        updatedMarkTag.setUuid(UPDATED_UUID);
        updatedMarkTag.setName(UPDATED_NAME);
        updatedMarkTag.setRecordStatus(UPDATED_RECORD_STATUS);

        restMarkTagMockMvc.perform(put("/api/mark-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarkTag)))
            .andExpect(status().isOk());

        // Validate the MarkTag in the database
        List<MarkTag> markTagList = markTagRepository.findAll();
        assertThat(markTagList).hasSize(databaseSizeBeforeUpdate);
        MarkTag testMarkTag = markTagList.get(markTagList.size() - 1);
        assertThat(testMarkTag.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testMarkTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarkTag.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMarkTag() throws Exception {
        int databaseSizeBeforeUpdate = markTagRepository.findAll().size();

        // Create the MarkTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarkTagMockMvc.perform(put("/api/mark-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markTag)))
            .andExpect(status().isCreated());

        // Validate the MarkTag in the database
        List<MarkTag> markTagList = markTagRepository.findAll();
        assertThat(markTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarkTag() throws Exception {
        // Initialize the database
        markTagRepository.saveAndFlush(markTag);
        int databaseSizeBeforeDelete = markTagRepository.findAll().size();

        // Get the markTag
        restMarkTagMockMvc.perform(delete("/api/mark-tags/{id}", markTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarkTag> markTagList = markTagRepository.findAll();
        assertThat(markTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarkTag.class);
        MarkTag markTag1 = new MarkTag();
        markTag1.setId(1L);
        MarkTag markTag2 = new MarkTag();
        markTag2.setId(markTag1.getId());
        assertThat(markTag1).isEqualTo(markTag2);
        markTag2.setId(2L);
        assertThat(markTag1).isNotEqualTo(markTag2);
        markTag1.setId(null);
        assertThat(markTag1).isNotEqualTo(markTag2);
    }
}
