package bo.ylb.app.web.rest;

import static bo.ylb.app.domain.VersionDatosAsserts.*;
import static bo.ylb.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bo.ylb.app.IntegrationTest;
import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.repository.VersionDatosRepository;
import bo.ylb.app.service.VersionDatosService;
import bo.ylb.app.service.dto.VersionDatosDTO;
import bo.ylb.app.service.mapper.VersionDatosMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
 * Integration tests for the {@link VersionDatosResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VersionDatosResourceIT {

    private static final String DEFAULT_NOMBRE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_VERSION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_VERSION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VERSION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_VERSION = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CITE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_CITE_VERSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/version-datos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VersionDatosRepository versionDatosRepository;

    @Mock
    private VersionDatosRepository versionDatosRepositoryMock;

    @Autowired
    private VersionDatosMapper versionDatosMapper;

    @Mock
    private VersionDatosService versionDatosServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVersionDatosMockMvc;

    private VersionDatos versionDatos;

    private VersionDatos insertedVersionDatos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionDatos createEntity() {
        return new VersionDatos()
            .nombreVersion(DEFAULT_NOMBRE_VERSION)
            .fechaVersion(DEFAULT_FECHA_VERSION)
            .citeVersion(DEFAULT_CITE_VERSION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionDatos createUpdatedEntity() {
        return new VersionDatos()
            .nombreVersion(UPDATED_NOMBRE_VERSION)
            .fechaVersion(UPDATED_FECHA_VERSION)
            .citeVersion(UPDATED_CITE_VERSION);
    }

    @BeforeEach
    public void initTest() {
        versionDatos = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVersionDatos != null) {
            versionDatosRepository.delete(insertedVersionDatos);
            insertedVersionDatos = null;
        }
    }

    @Test
    @Transactional
    void createVersionDatos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);
        var returnedVersionDatosDTO = om.readValue(
            restVersionDatosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VersionDatosDTO.class
        );

        // Validate the VersionDatos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVersionDatos = versionDatosMapper.toEntity(returnedVersionDatosDTO);
        assertVersionDatosUpdatableFieldsEquals(returnedVersionDatos, getPersistedVersionDatos(returnedVersionDatos));

        insertedVersionDatos = returnedVersionDatos;
    }

    @Test
    @Transactional
    void createVersionDatosWithExistingId() throws Exception {
        // Create the VersionDatos with an existing ID
        versionDatos.setId(1L);
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionDatosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        versionDatos.setNombreVersion(null);

        // Create the VersionDatos, which fails.
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        restVersionDatosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        versionDatos.setFechaVersion(null);

        // Create the VersionDatos, which fails.
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        restVersionDatosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCiteVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        versionDatos.setCiteVersion(null);

        // Create the VersionDatos, which fails.
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        restVersionDatosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVersionDatos() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionDatos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreVersion").value(hasItem(DEFAULT_NOMBRE_VERSION)))
            .andExpect(jsonPath("$.[*].fechaVersion").value(hasItem(DEFAULT_FECHA_VERSION.toString())))
            .andExpect(jsonPath("$.[*].citeVersion").value(hasItem(DEFAULT_CITE_VERSION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVersionDatosWithEagerRelationshipsIsEnabled() throws Exception {
        when(versionDatosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVersionDatosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(versionDatosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVersionDatosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(versionDatosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVersionDatosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(versionDatosRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVersionDatos() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get the versionDatos
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL_ID, versionDatos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(versionDatos.getId().intValue()))
            .andExpect(jsonPath("$.nombreVersion").value(DEFAULT_NOMBRE_VERSION))
            .andExpect(jsonPath("$.fechaVersion").value(DEFAULT_FECHA_VERSION.toString()))
            .andExpect(jsonPath("$.citeVersion").value(DEFAULT_CITE_VERSION));
    }

    @Test
    @Transactional
    void getVersionDatosByIdFiltering() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        Long id = versionDatos.getId();

        defaultVersionDatosFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVersionDatosFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVersionDatosFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVersionDatosByNombreVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where nombreVersion equals to
        defaultVersionDatosFiltering("nombreVersion.equals=" + DEFAULT_NOMBRE_VERSION, "nombreVersion.equals=" + UPDATED_NOMBRE_VERSION);
    }

    @Test
    @Transactional
    void getAllVersionDatosByNombreVersionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where nombreVersion in
        defaultVersionDatosFiltering(
            "nombreVersion.in=" + DEFAULT_NOMBRE_VERSION + "," + UPDATED_NOMBRE_VERSION,
            "nombreVersion.in=" + UPDATED_NOMBRE_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByNombreVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where nombreVersion is not null
        defaultVersionDatosFiltering("nombreVersion.specified=true", "nombreVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllVersionDatosByNombreVersionContainsSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where nombreVersion contains
        defaultVersionDatosFiltering(
            "nombreVersion.contains=" + DEFAULT_NOMBRE_VERSION,
            "nombreVersion.contains=" + UPDATED_NOMBRE_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByNombreVersionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where nombreVersion does not contain
        defaultVersionDatosFiltering(
            "nombreVersion.doesNotContain=" + UPDATED_NOMBRE_VERSION,
            "nombreVersion.doesNotContain=" + DEFAULT_NOMBRE_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion equals to
        defaultVersionDatosFiltering("fechaVersion.equals=" + DEFAULT_FECHA_VERSION, "fechaVersion.equals=" + UPDATED_FECHA_VERSION);
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion in
        defaultVersionDatosFiltering(
            "fechaVersion.in=" + DEFAULT_FECHA_VERSION + "," + UPDATED_FECHA_VERSION,
            "fechaVersion.in=" + UPDATED_FECHA_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion is not null
        defaultVersionDatosFiltering("fechaVersion.specified=true", "fechaVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion is greater than or equal to
        defaultVersionDatosFiltering(
            "fechaVersion.greaterThanOrEqual=" + DEFAULT_FECHA_VERSION,
            "fechaVersion.greaterThanOrEqual=" + UPDATED_FECHA_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion is less than or equal to
        defaultVersionDatosFiltering(
            "fechaVersion.lessThanOrEqual=" + DEFAULT_FECHA_VERSION,
            "fechaVersion.lessThanOrEqual=" + SMALLER_FECHA_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion is less than
        defaultVersionDatosFiltering("fechaVersion.lessThan=" + UPDATED_FECHA_VERSION, "fechaVersion.lessThan=" + DEFAULT_FECHA_VERSION);
    }

    @Test
    @Transactional
    void getAllVersionDatosByFechaVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where fechaVersion is greater than
        defaultVersionDatosFiltering(
            "fechaVersion.greaterThan=" + SMALLER_FECHA_VERSION,
            "fechaVersion.greaterThan=" + DEFAULT_FECHA_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByCiteVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where citeVersion equals to
        defaultVersionDatosFiltering("citeVersion.equals=" + DEFAULT_CITE_VERSION, "citeVersion.equals=" + UPDATED_CITE_VERSION);
    }

    @Test
    @Transactional
    void getAllVersionDatosByCiteVersionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where citeVersion in
        defaultVersionDatosFiltering(
            "citeVersion.in=" + DEFAULT_CITE_VERSION + "," + UPDATED_CITE_VERSION,
            "citeVersion.in=" + UPDATED_CITE_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByCiteVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where citeVersion is not null
        defaultVersionDatosFiltering("citeVersion.specified=true", "citeVersion.specified=false");
    }

    @Test
    @Transactional
    void getAllVersionDatosByCiteVersionContainsSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where citeVersion contains
        defaultVersionDatosFiltering("citeVersion.contains=" + DEFAULT_CITE_VERSION, "citeVersion.contains=" + UPDATED_CITE_VERSION);
    }

    @Test
    @Transactional
    void getAllVersionDatosByCiteVersionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        // Get all the versionDatosList where citeVersion does not contain
        defaultVersionDatosFiltering(
            "citeVersion.doesNotContain=" + UPDATED_CITE_VERSION,
            "citeVersion.doesNotContain=" + DEFAULT_CITE_VERSION
        );
    }

    @Test
    @Transactional
    void getAllVersionDatosByProyectoIsEqualToSomething() throws Exception {
        Proyecto proyecto;
        if (TestUtil.findAll(em, Proyecto.class).isEmpty()) {
            versionDatosRepository.saveAndFlush(versionDatos);
            proyecto = ProyectoResourceIT.createEntity();
        } else {
            proyecto = TestUtil.findAll(em, Proyecto.class).get(0);
        }
        em.persist(proyecto);
        em.flush();
        versionDatos.setProyecto(proyecto);
        versionDatosRepository.saveAndFlush(versionDatos);
        Long proyectoId = proyecto.getId();
        // Get all the versionDatosList where proyecto equals to proyectoId
        defaultVersionDatosShouldBeFound("proyectoId.equals=" + proyectoId);

        // Get all the versionDatosList where proyecto equals to (proyectoId + 1)
        defaultVersionDatosShouldNotBeFound("proyectoId.equals=" + (proyectoId + 1));
    }

    private void defaultVersionDatosFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVersionDatosShouldBeFound(shouldBeFound);
        defaultVersionDatosShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVersionDatosShouldBeFound(String filter) throws Exception {
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionDatos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreVersion").value(hasItem(DEFAULT_NOMBRE_VERSION)))
            .andExpect(jsonPath("$.[*].fechaVersion").value(hasItem(DEFAULT_FECHA_VERSION.toString())))
            .andExpect(jsonPath("$.[*].citeVersion").value(hasItem(DEFAULT_CITE_VERSION)));

        // Check, that the count call also returns 1
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVersionDatosShouldNotBeFound(String filter) throws Exception {
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVersionDatosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVersionDatos() throws Exception {
        // Get the versionDatos
        restVersionDatosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVersionDatos() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versionDatos
        VersionDatos updatedVersionDatos = versionDatosRepository.findById(versionDatos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVersionDatos are not directly saved in db
        em.detach(updatedVersionDatos);
        updatedVersionDatos.nombreVersion(UPDATED_NOMBRE_VERSION).fechaVersion(UPDATED_FECHA_VERSION).citeVersion(UPDATED_CITE_VERSION);
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(updatedVersionDatos);

        restVersionDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, versionDatosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(versionDatosDTO))
            )
            .andExpect(status().isOk());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVersionDatosToMatchAllProperties(updatedVersionDatos);
    }

    @Test
    @Transactional
    void putNonExistingVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, versionDatosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(versionDatosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(versionDatosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVersionDatosWithPatch() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versionDatos using partial update
        VersionDatos partialUpdatedVersionDatos = new VersionDatos();
        partialUpdatedVersionDatos.setId(versionDatos.getId());

        partialUpdatedVersionDatos
            .nombreVersion(UPDATED_NOMBRE_VERSION)
            .fechaVersion(UPDATED_FECHA_VERSION)
            .citeVersion(UPDATED_CITE_VERSION);

        restVersionDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVersionDatos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVersionDatos))
            )
            .andExpect(status().isOk());

        // Validate the VersionDatos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVersionDatosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVersionDatos, versionDatos),
            getPersistedVersionDatos(versionDatos)
        );
    }

    @Test
    @Transactional
    void fullUpdateVersionDatosWithPatch() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versionDatos using partial update
        VersionDatos partialUpdatedVersionDatos = new VersionDatos();
        partialUpdatedVersionDatos.setId(versionDatos.getId());

        partialUpdatedVersionDatos
            .nombreVersion(UPDATED_NOMBRE_VERSION)
            .fechaVersion(UPDATED_FECHA_VERSION)
            .citeVersion(UPDATED_CITE_VERSION);

        restVersionDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVersionDatos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVersionDatos))
            )
            .andExpect(status().isOk());

        // Validate the VersionDatos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVersionDatosUpdatableFieldsEquals(partialUpdatedVersionDatos, getPersistedVersionDatos(partialUpdatedVersionDatos));
    }

    @Test
    @Transactional
    void patchNonExistingVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, versionDatosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(versionDatosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(versionDatosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVersionDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versionDatos.setId(longCount.incrementAndGet());

        // Create the VersionDatos
        VersionDatosDTO versionDatosDTO = versionDatosMapper.toDto(versionDatos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersionDatosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(versionDatosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VersionDatos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVersionDatos() throws Exception {
        // Initialize the database
        insertedVersionDatos = versionDatosRepository.saveAndFlush(versionDatos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the versionDatos
        restVersionDatosMockMvc
            .perform(delete(ENTITY_API_URL_ID, versionDatos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return versionDatosRepository.count();
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

    protected VersionDatos getPersistedVersionDatos(VersionDatos versionDatos) {
        return versionDatosRepository.findById(versionDatos.getId()).orElseThrow();
    }

    protected void assertPersistedVersionDatosToMatchAllProperties(VersionDatos expectedVersionDatos) {
        assertVersionDatosAllPropertiesEquals(expectedVersionDatos, getPersistedVersionDatos(expectedVersionDatos));
    }

    protected void assertPersistedVersionDatosToMatchUpdatableProperties(VersionDatos expectedVersionDatos) {
        assertVersionDatosAllUpdatablePropertiesEquals(expectedVersionDatos, getPersistedVersionDatos(expectedVersionDatos));
    }
}
