package bo.ylb.app.web.rest;

import static bo.ylb.app.domain.DatosImagenAsserts.*;
import static bo.ylb.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bo.ylb.app.IntegrationTest;
import bo.ylb.app.domain.DatosImagen;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.repository.DatosImagenRepository;
import bo.ylb.app.service.DatosImagenService;
import bo.ylb.app.service.dto.DatosImagenDTO;
import bo.ylb.app.service.mapper.DatosImagenMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DatosImagenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DatosImagenResourceIT {

    private static final byte[] DEFAULT_IMAGEN_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_4_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_5 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_5 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_5_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_5_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_6 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_6 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_6_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_6_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_7 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_7 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_7_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_7_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_8 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_8 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_8_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_8_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_9 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_9 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_9_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_9_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_10 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_10 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_10_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_10_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_11 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_11 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_11_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_11_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_12 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_12 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_12_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_12_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/datos-imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DatosImagenRepository datosImagenRepository;

    @Mock
    private DatosImagenRepository datosImagenRepositoryMock;

    @Autowired
    private DatosImagenMapper datosImagenMapper;

    @Mock
    private DatosImagenService datosImagenServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatosImagenMockMvc;

    private DatosImagen datosImagen;

    private DatosImagen insertedDatosImagen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosImagen createEntity() {
        return new DatosImagen()
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .imagen3(DEFAULT_IMAGEN_3)
            .imagen3ContentType(DEFAULT_IMAGEN_3_CONTENT_TYPE)
            .imagen4(DEFAULT_IMAGEN_4)
            .imagen4ContentType(DEFAULT_IMAGEN_4_CONTENT_TYPE)
            .imagen5(DEFAULT_IMAGEN_5)
            .imagen5ContentType(DEFAULT_IMAGEN_5_CONTENT_TYPE)
            .imagen6(DEFAULT_IMAGEN_6)
            .imagen6ContentType(DEFAULT_IMAGEN_6_CONTENT_TYPE)
            .imagen7(DEFAULT_IMAGEN_7)
            .imagen7ContentType(DEFAULT_IMAGEN_7_CONTENT_TYPE)
            .imagen8(DEFAULT_IMAGEN_8)
            .imagen8ContentType(DEFAULT_IMAGEN_8_CONTENT_TYPE)
            .imagen9(DEFAULT_IMAGEN_9)
            .imagen9ContentType(DEFAULT_IMAGEN_9_CONTENT_TYPE)
            .imagen10(DEFAULT_IMAGEN_10)
            .imagen10ContentType(DEFAULT_IMAGEN_10_CONTENT_TYPE)
            .imagen11(DEFAULT_IMAGEN_11)
            .imagen11ContentType(DEFAULT_IMAGEN_11_CONTENT_TYPE)
            .imagen12(DEFAULT_IMAGEN_12)
            .imagen12ContentType(DEFAULT_IMAGEN_12_CONTENT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosImagen createUpdatedEntity() {
        return new DatosImagen()
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE)
            .imagen7(UPDATED_IMAGEN_7)
            .imagen7ContentType(UPDATED_IMAGEN_7_CONTENT_TYPE)
            .imagen8(UPDATED_IMAGEN_8)
            .imagen8ContentType(UPDATED_IMAGEN_8_CONTENT_TYPE)
            .imagen9(UPDATED_IMAGEN_9)
            .imagen9ContentType(UPDATED_IMAGEN_9_CONTENT_TYPE)
            .imagen10(UPDATED_IMAGEN_10)
            .imagen10ContentType(UPDATED_IMAGEN_10_CONTENT_TYPE)
            .imagen11(UPDATED_IMAGEN_11)
            .imagen11ContentType(UPDATED_IMAGEN_11_CONTENT_TYPE)
            .imagen12(UPDATED_IMAGEN_12)
            .imagen12ContentType(UPDATED_IMAGEN_12_CONTENT_TYPE);
    }

    @BeforeEach
    public void initTest() {
        datosImagen = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDatosImagen != null) {
            datosImagenRepository.delete(insertedDatosImagen);
            insertedDatosImagen = null;
        }
    }

    @Test
    @Transactional
    void createDatosImagen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);
        var returnedDatosImagenDTO = om.readValue(
            restDatosImagenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosImagenDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DatosImagenDTO.class
        );

        // Validate the DatosImagen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDatosImagen = datosImagenMapper.toEntity(returnedDatosImagenDTO);
        assertDatosImagenUpdatableFieldsEquals(returnedDatosImagen, getPersistedDatosImagen(returnedDatosImagen));

        insertedDatosImagen = returnedDatosImagen;
    }

    @Test
    @Transactional
    void createDatosImagenWithExistingId() throws Exception {
        // Create the DatosImagen with an existing ID
        datosImagen.setId(1L);
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatosImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDatosImagens() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        // Get all the datosImagenList
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_6))))
            .andExpect(jsonPath("$.[*].imagen7ContentType").value(hasItem(DEFAULT_IMAGEN_7_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen7").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_7))))
            .andExpect(jsonPath("$.[*].imagen8ContentType").value(hasItem(DEFAULT_IMAGEN_8_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen8").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_8))))
            .andExpect(jsonPath("$.[*].imagen9ContentType").value(hasItem(DEFAULT_IMAGEN_9_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen9").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_9))))
            .andExpect(jsonPath("$.[*].imagen10ContentType").value(hasItem(DEFAULT_IMAGEN_10_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen10").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_10))))
            .andExpect(jsonPath("$.[*].imagen11ContentType").value(hasItem(DEFAULT_IMAGEN_11_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen11").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_11))))
            .andExpect(jsonPath("$.[*].imagen12ContentType").value(hasItem(DEFAULT_IMAGEN_12_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen12").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_12))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatosImagensWithEagerRelationshipsIsEnabled() throws Exception {
        when(datosImagenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatosImagenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(datosImagenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatosImagensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(datosImagenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatosImagenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(datosImagenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDatosImagen() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        // Get the datosImagen
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL_ID, datosImagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datosImagen.getId().intValue()))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.imagen3ContentType").value(DEFAULT_IMAGEN_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen3").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_3)))
            .andExpect(jsonPath("$.imagen4ContentType").value(DEFAULT_IMAGEN_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen4").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_4)))
            .andExpect(jsonPath("$.imagen5ContentType").value(DEFAULT_IMAGEN_5_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen5").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_5)))
            .andExpect(jsonPath("$.imagen6ContentType").value(DEFAULT_IMAGEN_6_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen6").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_6)))
            .andExpect(jsonPath("$.imagen7ContentType").value(DEFAULT_IMAGEN_7_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen7").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_7)))
            .andExpect(jsonPath("$.imagen8ContentType").value(DEFAULT_IMAGEN_8_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen8").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_8)))
            .andExpect(jsonPath("$.imagen9ContentType").value(DEFAULT_IMAGEN_9_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen9").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_9)))
            .andExpect(jsonPath("$.imagen10ContentType").value(DEFAULT_IMAGEN_10_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen10").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_10)))
            .andExpect(jsonPath("$.imagen11ContentType").value(DEFAULT_IMAGEN_11_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen11").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_11)))
            .andExpect(jsonPath("$.imagen12ContentType").value(DEFAULT_IMAGEN_12_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen12").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_12)));
    }

    @Test
    @Transactional
    void getDatosImagensByIdFiltering() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        Long id = datosImagen.getId();

        defaultDatosImagenFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDatosImagenFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDatosImagenFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDatosImagensByVersionDatosIsEqualToSomething() throws Exception {
        VersionDatos versionDatos;
        if (TestUtil.findAll(em, VersionDatos.class).isEmpty()) {
            datosImagenRepository.saveAndFlush(datosImagen);
            versionDatos = VersionDatosResourceIT.createEntity();
        } else {
            versionDatos = TestUtil.findAll(em, VersionDatos.class).get(0);
        }
        em.persist(versionDatos);
        em.flush();
        datosImagen.setVersionDatos(versionDatos);
        datosImagenRepository.saveAndFlush(datosImagen);
        Long versionDatosId = versionDatos.getId();
        // Get all the datosImagenList where versionDatos equals to versionDatosId
        defaultDatosImagenShouldBeFound("versionDatosId.equals=" + versionDatosId);

        // Get all the datosImagenList where versionDatos equals to (versionDatosId + 1)
        defaultDatosImagenShouldNotBeFound("versionDatosId.equals=" + (versionDatosId + 1));
    }

    private void defaultDatosImagenFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDatosImagenShouldBeFound(shouldBeFound);
        defaultDatosImagenShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatosImagenShouldBeFound(String filter) throws Exception {
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_6))))
            .andExpect(jsonPath("$.[*].imagen7ContentType").value(hasItem(DEFAULT_IMAGEN_7_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen7").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_7))))
            .andExpect(jsonPath("$.[*].imagen8ContentType").value(hasItem(DEFAULT_IMAGEN_8_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen8").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_8))))
            .andExpect(jsonPath("$.[*].imagen9ContentType").value(hasItem(DEFAULT_IMAGEN_9_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen9").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_9))))
            .andExpect(jsonPath("$.[*].imagen10ContentType").value(hasItem(DEFAULT_IMAGEN_10_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen10").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_10))))
            .andExpect(jsonPath("$.[*].imagen11ContentType").value(hasItem(DEFAULT_IMAGEN_11_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen11").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_11))))
            .andExpect(jsonPath("$.[*].imagen12ContentType").value(hasItem(DEFAULT_IMAGEN_12_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen12").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_12))));

        // Check, that the count call also returns 1
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatosImagenShouldNotBeFound(String filter) throws Exception {
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatosImagenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDatosImagen() throws Exception {
        // Get the datosImagen
        restDatosImagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDatosImagen() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datosImagen
        DatosImagen updatedDatosImagen = datosImagenRepository.findById(datosImagen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDatosImagen are not directly saved in db
        em.detach(updatedDatosImagen);
        updatedDatosImagen
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE)
            .imagen7(UPDATED_IMAGEN_7)
            .imagen7ContentType(UPDATED_IMAGEN_7_CONTENT_TYPE)
            .imagen8(UPDATED_IMAGEN_8)
            .imagen8ContentType(UPDATED_IMAGEN_8_CONTENT_TYPE)
            .imagen9(UPDATED_IMAGEN_9)
            .imagen9ContentType(UPDATED_IMAGEN_9_CONTENT_TYPE)
            .imagen10(UPDATED_IMAGEN_10)
            .imagen10ContentType(UPDATED_IMAGEN_10_CONTENT_TYPE)
            .imagen11(UPDATED_IMAGEN_11)
            .imagen11ContentType(UPDATED_IMAGEN_11_CONTENT_TYPE)
            .imagen12(UPDATED_IMAGEN_12)
            .imagen12ContentType(UPDATED_IMAGEN_12_CONTENT_TYPE);
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(updatedDatosImagen);

        restDatosImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datosImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(datosImagenDTO))
            )
            .andExpect(status().isOk());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDatosImagenToMatchAllProperties(updatedDatosImagen);
    }

    @Test
    @Transactional
    void putNonExistingDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datosImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(datosImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(datosImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosImagenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatosImagenWithPatch() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datosImagen using partial update
        DatosImagen partialUpdatedDatosImagen = new DatosImagen();
        partialUpdatedDatosImagen.setId(datosImagen.getId());

        partialUpdatedDatosImagen
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen9(UPDATED_IMAGEN_9)
            .imagen9ContentType(UPDATED_IMAGEN_9_CONTENT_TYPE)
            .imagen12(UPDATED_IMAGEN_12)
            .imagen12ContentType(UPDATED_IMAGEN_12_CONTENT_TYPE);

        restDatosImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatosImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatosImagen))
            )
            .andExpect(status().isOk());

        // Validate the DatosImagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatosImagenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDatosImagen, datosImagen),
            getPersistedDatosImagen(datosImagen)
        );
    }

    @Test
    @Transactional
    void fullUpdateDatosImagenWithPatch() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datosImagen using partial update
        DatosImagen partialUpdatedDatosImagen = new DatosImagen();
        partialUpdatedDatosImagen.setId(datosImagen.getId());

        partialUpdatedDatosImagen
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE)
            .imagen7(UPDATED_IMAGEN_7)
            .imagen7ContentType(UPDATED_IMAGEN_7_CONTENT_TYPE)
            .imagen8(UPDATED_IMAGEN_8)
            .imagen8ContentType(UPDATED_IMAGEN_8_CONTENT_TYPE)
            .imagen9(UPDATED_IMAGEN_9)
            .imagen9ContentType(UPDATED_IMAGEN_9_CONTENT_TYPE)
            .imagen10(UPDATED_IMAGEN_10)
            .imagen10ContentType(UPDATED_IMAGEN_10_CONTENT_TYPE)
            .imagen11(UPDATED_IMAGEN_11)
            .imagen11ContentType(UPDATED_IMAGEN_11_CONTENT_TYPE)
            .imagen12(UPDATED_IMAGEN_12)
            .imagen12ContentType(UPDATED_IMAGEN_12_CONTENT_TYPE);

        restDatosImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatosImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatosImagen))
            )
            .andExpect(status().isOk());

        // Validate the DatosImagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatosImagenUpdatableFieldsEquals(partialUpdatedDatosImagen, getPersistedDatosImagen(partialUpdatedDatosImagen));
    }

    @Test
    @Transactional
    void patchNonExistingDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, datosImagenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datosImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datosImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDatosImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datosImagen.setId(longCount.incrementAndGet());

        // Create the DatosImagen
        DatosImagenDTO datosImagenDTO = datosImagenMapper.toDto(datosImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosImagenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(datosImagenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DatosImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDatosImagen() throws Exception {
        // Initialize the database
        insertedDatosImagen = datosImagenRepository.saveAndFlush(datosImagen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the datosImagen
        restDatosImagenMockMvc
            .perform(delete(ENTITY_API_URL_ID, datosImagen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return datosImagenRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected DatosImagen getPersistedDatosImagen(DatosImagen datosImagen) {
        return datosImagenRepository.findById(datosImagen.getId()).orElseThrow();
    }

    protected void assertPersistedDatosImagenToMatchAllProperties(DatosImagen expectedDatosImagen) {
        assertDatosImagenAllPropertiesEquals(expectedDatosImagen, getPersistedDatosImagen(expectedDatosImagen));
    }

    protected void assertPersistedDatosImagenToMatchUpdatableProperties(DatosImagen expectedDatosImagen) {
        assertDatosImagenAllUpdatablePropertiesEquals(expectedDatosImagen, getPersistedDatosImagen(expectedDatosImagen));
    }
}
