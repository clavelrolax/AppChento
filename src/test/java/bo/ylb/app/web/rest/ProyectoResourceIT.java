package bo.ylb.app.web.rest;

import static bo.ylb.app.domain.ProyectoAsserts.*;
import static bo.ylb.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bo.ylb.app.IntegrationTest;
import bo.ylb.app.domain.Operador;
import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.repository.ProyectoRepository;
import bo.ylb.app.service.ProyectoService;
import bo.ylb.app.service.dto.ProyectoDTO;
import bo.ylb.app.service.mapper.ProyectoMapper;
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
 * Integration tests for the {@link ProyectoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProyectoResourceIT {

    private static final String DEFAULT_NOMBRE_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_OBJETIVO = "AAAAAAAAAA";
    private static final String UPDATED_OBJETIVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIEMPO_PROYECTO = 1;
    private static final Integer UPDATED_TIEMPO_PROYECTO = 2;
    private static final Integer SMALLER_TIEMPO_PROYECTO = 1 - 1;

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_INICIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_FIN = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/proyectos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Mock
    private ProyectoRepository proyectoRepositoryMock;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Mock
    private ProyectoService proyectoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProyectoMockMvc;

    private Proyecto proyecto;

    private Proyecto insertedProyecto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createEntity() {
        return new Proyecto()
            .nombreProyecto(DEFAULT_NOMBRE_PROYECTO)
            .objetivo(DEFAULT_OBJETIVO)
            .tiempoProyecto(DEFAULT_TIEMPO_PROYECTO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createUpdatedEntity() {
        return new Proyecto()
            .nombreProyecto(UPDATED_NOMBRE_PROYECTO)
            .objetivo(UPDATED_OBJETIVO)
            .tiempoProyecto(UPDATED_TIEMPO_PROYECTO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
    }

    @BeforeEach
    public void initTest() {
        proyecto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProyecto != null) {
            proyectoRepository.delete(insertedProyecto);
            insertedProyecto = null;
        }
    }

    @Test
    @Transactional
    void createProyecto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);
        var returnedProyectoDTO = om.readValue(
            restProyectoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProyectoDTO.class
        );

        // Validate the Proyecto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProyecto = proyectoMapper.toEntity(returnedProyectoDTO);
        assertProyectoUpdatableFieldsEquals(returnedProyecto, getPersistedProyecto(returnedProyecto));

        insertedProyecto = returnedProyecto;
    }

    @Test
    @Transactional
    void createProyectoWithExistingId() throws Exception {
        // Create the Proyecto with an existing ID
        proyecto.setId(1L);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreProyectoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        proyecto.setNombreProyecto(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        restProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObjetivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        proyecto.setObjetivo(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        restProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTiempoProyectoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        proyecto.setTiempoProyecto(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        restProyectoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProyectos() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProyecto").value(hasItem(DEFAULT_NOMBRE_PROYECTO)))
            .andExpect(jsonPath("$.[*].objetivo").value(hasItem(DEFAULT_OBJETIVO)))
            .andExpect(jsonPath("$.[*].tiempoProyecto").value(hasItem(DEFAULT_TIEMPO_PROYECTO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProyectosWithEagerRelationshipsIsEnabled() throws Exception {
        when(proyectoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProyectoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(proyectoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProyectosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(proyectoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProyectoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(proyectoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProyecto() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get the proyecto
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL_ID, proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyecto.getId().intValue()))
            .andExpect(jsonPath("$.nombreProyecto").value(DEFAULT_NOMBRE_PROYECTO))
            .andExpect(jsonPath("$.objetivo").value(DEFAULT_OBJETIVO))
            .andExpect(jsonPath("$.tiempoProyecto").value(DEFAULT_TIEMPO_PROYECTO))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    void getProyectosByIdFiltering() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        Long id = proyecto.getId();

        defaultProyectoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProyectoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProyectoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProyectosByNombreProyectoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where nombreProyecto equals to
        defaultProyectoFiltering("nombreProyecto.equals=" + DEFAULT_NOMBRE_PROYECTO, "nombreProyecto.equals=" + UPDATED_NOMBRE_PROYECTO);
    }

    @Test
    @Transactional
    void getAllProyectosByNombreProyectoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where nombreProyecto in
        defaultProyectoFiltering(
            "nombreProyecto.in=" + DEFAULT_NOMBRE_PROYECTO + "," + UPDATED_NOMBRE_PROYECTO,
            "nombreProyecto.in=" + UPDATED_NOMBRE_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByNombreProyectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where nombreProyecto is not null
        defaultProyectoFiltering("nombreProyecto.specified=true", "nombreProyecto.specified=false");
    }

    @Test
    @Transactional
    void getAllProyectosByNombreProyectoContainsSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where nombreProyecto contains
        defaultProyectoFiltering(
            "nombreProyecto.contains=" + DEFAULT_NOMBRE_PROYECTO,
            "nombreProyecto.contains=" + UPDATED_NOMBRE_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByNombreProyectoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where nombreProyecto does not contain
        defaultProyectoFiltering(
            "nombreProyecto.doesNotContain=" + UPDATED_NOMBRE_PROYECTO,
            "nombreProyecto.doesNotContain=" + DEFAULT_NOMBRE_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByObjetivoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where objetivo equals to
        defaultProyectoFiltering("objetivo.equals=" + DEFAULT_OBJETIVO, "objetivo.equals=" + UPDATED_OBJETIVO);
    }

    @Test
    @Transactional
    void getAllProyectosByObjetivoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where objetivo in
        defaultProyectoFiltering("objetivo.in=" + DEFAULT_OBJETIVO + "," + UPDATED_OBJETIVO, "objetivo.in=" + UPDATED_OBJETIVO);
    }

    @Test
    @Transactional
    void getAllProyectosByObjetivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where objetivo is not null
        defaultProyectoFiltering("objetivo.specified=true", "objetivo.specified=false");
    }

    @Test
    @Transactional
    void getAllProyectosByObjetivoContainsSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where objetivo contains
        defaultProyectoFiltering("objetivo.contains=" + DEFAULT_OBJETIVO, "objetivo.contains=" + UPDATED_OBJETIVO);
    }

    @Test
    @Transactional
    void getAllProyectosByObjetivoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where objetivo does not contain
        defaultProyectoFiltering("objetivo.doesNotContain=" + UPDATED_OBJETIVO, "objetivo.doesNotContain=" + DEFAULT_OBJETIVO);
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto equals to
        defaultProyectoFiltering("tiempoProyecto.equals=" + DEFAULT_TIEMPO_PROYECTO, "tiempoProyecto.equals=" + UPDATED_TIEMPO_PROYECTO);
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto in
        defaultProyectoFiltering(
            "tiempoProyecto.in=" + DEFAULT_TIEMPO_PROYECTO + "," + UPDATED_TIEMPO_PROYECTO,
            "tiempoProyecto.in=" + UPDATED_TIEMPO_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto is not null
        defaultProyectoFiltering("tiempoProyecto.specified=true", "tiempoProyecto.specified=false");
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto is greater than or equal to
        defaultProyectoFiltering(
            "tiempoProyecto.greaterThanOrEqual=" + DEFAULT_TIEMPO_PROYECTO,
            "tiempoProyecto.greaterThanOrEqual=" + UPDATED_TIEMPO_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto is less than or equal to
        defaultProyectoFiltering(
            "tiempoProyecto.lessThanOrEqual=" + DEFAULT_TIEMPO_PROYECTO,
            "tiempoProyecto.lessThanOrEqual=" + SMALLER_TIEMPO_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto is less than
        defaultProyectoFiltering(
            "tiempoProyecto.lessThan=" + UPDATED_TIEMPO_PROYECTO,
            "tiempoProyecto.lessThan=" + DEFAULT_TIEMPO_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByTiempoProyectoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where tiempoProyecto is greater than
        defaultProyectoFiltering(
            "tiempoProyecto.greaterThan=" + SMALLER_TIEMPO_PROYECTO,
            "tiempoProyecto.greaterThan=" + DEFAULT_TIEMPO_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio equals to
        defaultProyectoFiltering("fechaInicio.equals=" + DEFAULT_FECHA_INICIO, "fechaInicio.equals=" + UPDATED_FECHA_INICIO);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio in
        defaultProyectoFiltering(
            "fechaInicio.in=" + DEFAULT_FECHA_INICIO + "," + UPDATED_FECHA_INICIO,
            "fechaInicio.in=" + UPDATED_FECHA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio is not null
        defaultProyectoFiltering("fechaInicio.specified=true", "fechaInicio.specified=false");
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio is greater than or equal to
        defaultProyectoFiltering(
            "fechaInicio.greaterThanOrEqual=" + DEFAULT_FECHA_INICIO,
            "fechaInicio.greaterThanOrEqual=" + UPDATED_FECHA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio is less than or equal to
        defaultProyectoFiltering(
            "fechaInicio.lessThanOrEqual=" + DEFAULT_FECHA_INICIO,
            "fechaInicio.lessThanOrEqual=" + SMALLER_FECHA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio is less than
        defaultProyectoFiltering("fechaInicio.lessThan=" + UPDATED_FECHA_INICIO, "fechaInicio.lessThan=" + DEFAULT_FECHA_INICIO);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaInicio is greater than
        defaultProyectoFiltering("fechaInicio.greaterThan=" + SMALLER_FECHA_INICIO, "fechaInicio.greaterThan=" + DEFAULT_FECHA_INICIO);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin equals to
        defaultProyectoFiltering("fechaFin.equals=" + DEFAULT_FECHA_FIN, "fechaFin.equals=" + UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin in
        defaultProyectoFiltering("fechaFin.in=" + DEFAULT_FECHA_FIN + "," + UPDATED_FECHA_FIN, "fechaFin.in=" + UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin is not null
        defaultProyectoFiltering("fechaFin.specified=true", "fechaFin.specified=false");
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin is greater than or equal to
        defaultProyectoFiltering("fechaFin.greaterThanOrEqual=" + DEFAULT_FECHA_FIN, "fechaFin.greaterThanOrEqual=" + UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin is less than or equal to
        defaultProyectoFiltering("fechaFin.lessThanOrEqual=" + DEFAULT_FECHA_FIN, "fechaFin.lessThanOrEqual=" + SMALLER_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin is less than
        defaultProyectoFiltering("fechaFin.lessThan=" + UPDATED_FECHA_FIN, "fechaFin.lessThan=" + DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByFechaFinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList where fechaFin is greater than
        defaultProyectoFiltering("fechaFin.greaterThan=" + SMALLER_FECHA_FIN, "fechaFin.greaterThan=" + DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    void getAllProyectosByOperadorIsEqualToSomething() throws Exception {
        Operador operador;
        if (TestUtil.findAll(em, Operador.class).isEmpty()) {
            proyectoRepository.saveAndFlush(proyecto);
            operador = OperadorResourceIT.createEntity();
        } else {
            operador = TestUtil.findAll(em, Operador.class).get(0);
        }
        em.persist(operador);
        em.flush();
        proyecto.setOperador(operador);
        proyectoRepository.saveAndFlush(proyecto);
        Long operadorId = operador.getId();
        // Get all the proyectoList where operador equals to operadorId
        defaultProyectoShouldBeFound("operadorId.equals=" + operadorId);

        // Get all the proyectoList where operador equals to (operadorId + 1)
        defaultProyectoShouldNotBeFound("operadorId.equals=" + (operadorId + 1));
    }

    private void defaultProyectoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProyectoShouldBeFound(shouldBeFound);
        defaultProyectoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProyectoShouldBeFound(String filter) throws Exception {
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProyecto").value(hasItem(DEFAULT_NOMBRE_PROYECTO)))
            .andExpect(jsonPath("$.[*].objetivo").value(hasItem(DEFAULT_OBJETIVO)))
            .andExpect(jsonPath("$.[*].tiempoProyecto").value(hasItem(DEFAULT_TIEMPO_PROYECTO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));

        // Check, that the count call also returns 1
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProyectoShouldNotBeFound(String filter) throws Exception {
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProyectoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProyecto() throws Exception {
        // Get the proyecto
        restProyectoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProyecto() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proyecto
        Proyecto updatedProyecto = proyectoRepository.findById(proyecto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProyecto are not directly saved in db
        em.detach(updatedProyecto);
        updatedProyecto
            .nombreProyecto(UPDATED_NOMBRE_PROYECTO)
            .objetivo(UPDATED_OBJETIVO)
            .tiempoProyecto(UPDATED_TIEMPO_PROYECTO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(updatedProyecto);

        restProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proyectoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proyectoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProyectoToMatchAllProperties(updatedProyecto);
    }

    @Test
    @Transactional
    void putNonExistingProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proyectoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proyectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proyectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProyectoWithPatch() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proyecto using partial update
        Proyecto partialUpdatedProyecto = new Proyecto();
        partialUpdatedProyecto.setId(proyecto.getId());

        partialUpdatedProyecto.nombreProyecto(UPDATED_NOMBRE_PROYECTO).fechaInicio(UPDATED_FECHA_INICIO).fechaFin(UPDATED_FECHA_FIN);

        restProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProyecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProyecto))
            )
            .andExpect(status().isOk());

        // Validate the Proyecto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProyectoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProyecto, proyecto), getPersistedProyecto(proyecto));
    }

    @Test
    @Transactional
    void fullUpdateProyectoWithPatch() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proyecto using partial update
        Proyecto partialUpdatedProyecto = new Proyecto();
        partialUpdatedProyecto.setId(proyecto.getId());

        partialUpdatedProyecto
            .nombreProyecto(UPDATED_NOMBRE_PROYECTO)
            .objetivo(UPDATED_OBJETIVO)
            .tiempoProyecto(UPDATED_TIEMPO_PROYECTO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProyecto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProyecto))
            )
            .andExpect(status().isOk());

        // Validate the Proyecto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProyectoUpdatableFieldsEquals(partialUpdatedProyecto, getPersistedProyecto(partialUpdatedProyecto));
    }

    @Test
    @Transactional
    void patchNonExistingProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proyectoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proyectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proyectoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProyecto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proyecto.setId(longCount.incrementAndGet());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProyectoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(proyectoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proyecto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProyecto() throws Exception {
        // Initialize the database
        insertedProyecto = proyectoRepository.saveAndFlush(proyecto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the proyecto
        restProyectoMockMvc
            .perform(delete(ENTITY_API_URL_ID, proyecto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return proyectoRepository.count();
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

    protected Proyecto getPersistedProyecto(Proyecto proyecto) {
        return proyectoRepository.findById(proyecto.getId()).orElseThrow();
    }

    protected void assertPersistedProyectoToMatchAllProperties(Proyecto expectedProyecto) {
        assertProyectoAllPropertiesEquals(expectedProyecto, getPersistedProyecto(expectedProyecto));
    }

    protected void assertPersistedProyectoToMatchUpdatableProperties(Proyecto expectedProyecto) {
        assertProyectoAllUpdatablePropertiesEquals(expectedProyecto, getPersistedProyecto(expectedProyecto));
    }
}
