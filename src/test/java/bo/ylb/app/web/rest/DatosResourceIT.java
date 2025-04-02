package bo.ylb.app.web.rest;

import static bo.ylb.app.domain.DatosAsserts.*;
import static bo.ylb.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bo.ylb.app.IntegrationTest;
import bo.ylb.app.domain.Datos;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.repository.DatosRepository;
import bo.ylb.app.service.DatosService;
import bo.ylb.app.service.dto.DatosDTO;
import bo.ylb.app.service.mapper.DatosMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link DatosResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DatosResourceIT {

    private static final String DEFAULT_INVERSION_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_INVERSION_TOTAL = "BBBBBBBBBB";

    private static final String DEFAULT_INGRESOSX_VENTAS = "AAAAAAAAAA";
    private static final String UPDATED_INGRESOSX_VENTAS = "BBBBBBBBBB";

    private static final String DEFAULT_GANANCIAS_YLB = "AAAAAAAAAA";
    private static final String UPDATED_GANANCIAS_YLB = "BBBBBBBBBB";

    private static final String DEFAULT_GOUBERNAMENT_TAK = "AAAAAAAAAA";
    private static final String UPDATED_GOUBERNAMENT_TAK = "BBBBBBBBBB";

    private static final String DEFAULT_REGALIAS = "AAAAAAAAAA";
    private static final String UPDATED_REGALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_IUE = "AAAAAAAAAA";
    private static final String UPDATED_IUE = "BBBBBBBBBB";

    private static final String DEFAULT_IVA = "AAAAAAAAAA";
    private static final String UPDATED_IVA = "BBBBBBBBBB";

    private static final String DEFAULT_IT = "AAAAAAAAAA";
    private static final String UPDATED_IT = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_PRECIO_VENTAPROM = "AAAAAAAAAA";
    private static final String UPDATED_T_1_PRECIO_VENTAPROM = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_COSTO_VARIABLE = "AAAAAAAAAA";
    private static final String UPDATED_T_1_COSTO_VARIABLE = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_COSTO_VARTARIFA = "AAAAAAAAAA";
    private static final String UPDATED_T_1_COSTO_VARTARIFA = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_MARGEN_UNITARIO = "AAAAAAAAAA";
    private static final String UPDATED_T_1_MARGEN_UNITARIO = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_COSTO_FIJO = "AAAAAAAAAA";
    private static final String UPDATED_T_1_COSTO_FIJO = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_COSTO_TOTALUNITPROM = "AAAAAAAAAA";
    private static final String UPDATED_T_1_COSTO_TOTALUNITPROM = "BBBBBBBBBB";

    private static final String DEFAULT_T_1_PUNTO_EQUILIBRIO = "AAAAAAAAAA";
    private static final String UPDATED_T_1_PUNTO_EQUILIBRIO = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_TASAINTERES = "AAAAAAAAAA";
    private static final String UPDATED_T_2_TASAINTERES = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_TASADESCUENTO = "AAAAAAAAAA";
    private static final String UPDATED_T_2_TASADESCUENTO = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_VANDEL_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_T_2_VANDEL_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_VAN_YLB = "AAAAAAAAAA";
    private static final String UPDATED_T_2_VAN_YLB = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_VP = "AAAAAAAAAA";
    private static final String UPDATED_T_2_VP = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_TIR_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_T_2_TIR_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_T_2_TIR_YLB = "AAAAAAAAAA";
    private static final String UPDATED_T_2_TIR_YLB = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/datos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DatosRepository datosRepository;

    @Mock
    private DatosRepository datosRepositoryMock;

    @Autowired
    private DatosMapper datosMapper;

    @Mock
    private DatosService datosServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatosMockMvc;

    private Datos datos;

    private Datos insertedDatos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Datos createEntity() {
        return new Datos()
            .inversionTotal(DEFAULT_INVERSION_TOTAL)
            .ingresosxVentas(DEFAULT_INGRESOSX_VENTAS)
            .gananciasYLB(DEFAULT_GANANCIAS_YLB)
            .goubernamentTak(DEFAULT_GOUBERNAMENT_TAK)
            .regalias(DEFAULT_REGALIAS)
            .iue(DEFAULT_IUE)
            .iva(DEFAULT_IVA)
            .it(DEFAULT_IT)
            .t1precioVentaprom(DEFAULT_T_1_PRECIO_VENTAPROM)
            .t1costoVariable(DEFAULT_T_1_COSTO_VARIABLE)
            .t1costoVartarifa(DEFAULT_T_1_COSTO_VARTARIFA)
            .t1margenUnitario(DEFAULT_T_1_MARGEN_UNITARIO)
            .t1costoFijo(DEFAULT_T_1_COSTO_FIJO)
            .t1costoTotalunitprom(DEFAULT_T_1_COSTO_TOTALUNITPROM)
            .t1puntoEquilibrio(DEFAULT_T_1_PUNTO_EQUILIBRIO)
            .t2tasainteres(DEFAULT_T_2_TASAINTERES)
            .t2tasadescuento(DEFAULT_T_2_TASADESCUENTO)
            .t2vandelProyecto(DEFAULT_T_2_VANDEL_PROYECTO)
            .t2vanYlb(DEFAULT_T_2_VAN_YLB)
            .t2vp(DEFAULT_T_2_VP)
            .t2tirProyecto(DEFAULT_T_2_TIR_PROYECTO)
            .t2tirYlb(DEFAULT_T_2_TIR_YLB);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Datos createUpdatedEntity() {
        return new Datos()
            .inversionTotal(UPDATED_INVERSION_TOTAL)
            .ingresosxVentas(UPDATED_INGRESOSX_VENTAS)
            .gananciasYLB(UPDATED_GANANCIAS_YLB)
            .goubernamentTak(UPDATED_GOUBERNAMENT_TAK)
            .regalias(UPDATED_REGALIAS)
            .iue(UPDATED_IUE)
            .iva(UPDATED_IVA)
            .it(UPDATED_IT)
            .t1precioVentaprom(UPDATED_T_1_PRECIO_VENTAPROM)
            .t1costoVariable(UPDATED_T_1_COSTO_VARIABLE)
            .t1costoVartarifa(UPDATED_T_1_COSTO_VARTARIFA)
            .t1margenUnitario(UPDATED_T_1_MARGEN_UNITARIO)
            .t1costoFijo(UPDATED_T_1_COSTO_FIJO)
            .t1costoTotalunitprom(UPDATED_T_1_COSTO_TOTALUNITPROM)
            .t1puntoEquilibrio(UPDATED_T_1_PUNTO_EQUILIBRIO)
            .t2tasainteres(UPDATED_T_2_TASAINTERES)
            .t2tasadescuento(UPDATED_T_2_TASADESCUENTO)
            .t2vandelProyecto(UPDATED_T_2_VANDEL_PROYECTO)
            .t2vanYlb(UPDATED_T_2_VAN_YLB)
            .t2vp(UPDATED_T_2_VP)
            .t2tirProyecto(UPDATED_T_2_TIR_PROYECTO)
            .t2tirYlb(UPDATED_T_2_TIR_YLB);
    }

    @BeforeEach
    public void initTest() {
        datos = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDatos != null) {
            datosRepository.delete(insertedDatos);
            insertedDatos = null;
        }
    }

    @Test
    @Transactional
    void createDatos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);
        var returnedDatosDTO = om.readValue(
            restDatosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DatosDTO.class
        );

        // Validate the Datos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDatos = datosMapper.toEntity(returnedDatosDTO);
        assertDatosUpdatableFieldsEquals(returnedDatos, getPersistedDatos(returnedDatos));

        insertedDatos = returnedDatos;
    }

    @Test
    @Transactional
    void createDatosWithExistingId() throws Exception {
        // Create the Datos with an existing ID
        datos.setId(1L);
        DatosDTO datosDTO = datosMapper.toDto(datos);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDatos() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList
        restDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datos.getId().intValue())))
            .andExpect(jsonPath("$.[*].inversionTotal").value(hasItem(DEFAULT_INVERSION_TOTAL)))
            .andExpect(jsonPath("$.[*].ingresosxVentas").value(hasItem(DEFAULT_INGRESOSX_VENTAS)))
            .andExpect(jsonPath("$.[*].gananciasYLB").value(hasItem(DEFAULT_GANANCIAS_YLB)))
            .andExpect(jsonPath("$.[*].goubernamentTak").value(hasItem(DEFAULT_GOUBERNAMENT_TAK)))
            .andExpect(jsonPath("$.[*].regalias").value(hasItem(DEFAULT_REGALIAS)))
            .andExpect(jsonPath("$.[*].iue").value(hasItem(DEFAULT_IUE)))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA)))
            .andExpect(jsonPath("$.[*].it").value(hasItem(DEFAULT_IT)))
            .andExpect(jsonPath("$.[*].t1precioVentaprom").value(hasItem(DEFAULT_T_1_PRECIO_VENTAPROM)))
            .andExpect(jsonPath("$.[*].t1costoVariable").value(hasItem(DEFAULT_T_1_COSTO_VARIABLE)))
            .andExpect(jsonPath("$.[*].t1costoVartarifa").value(hasItem(DEFAULT_T_1_COSTO_VARTARIFA)))
            .andExpect(jsonPath("$.[*].t1margenUnitario").value(hasItem(DEFAULT_T_1_MARGEN_UNITARIO)))
            .andExpect(jsonPath("$.[*].t1costoFijo").value(hasItem(DEFAULT_T_1_COSTO_FIJO)))
            .andExpect(jsonPath("$.[*].t1costoTotalunitprom").value(hasItem(DEFAULT_T_1_COSTO_TOTALUNITPROM)))
            .andExpect(jsonPath("$.[*].t1puntoEquilibrio").value(hasItem(DEFAULT_T_1_PUNTO_EQUILIBRIO)))
            .andExpect(jsonPath("$.[*].t2tasainteres").value(hasItem(DEFAULT_T_2_TASAINTERES)))
            .andExpect(jsonPath("$.[*].t2tasadescuento").value(hasItem(DEFAULT_T_2_TASADESCUENTO)))
            .andExpect(jsonPath("$.[*].t2vandelProyecto").value(hasItem(DEFAULT_T_2_VANDEL_PROYECTO)))
            .andExpect(jsonPath("$.[*].t2vanYlb").value(hasItem(DEFAULT_T_2_VAN_YLB)))
            .andExpect(jsonPath("$.[*].t2vp").value(hasItem(DEFAULT_T_2_VP)))
            .andExpect(jsonPath("$.[*].t2tirProyecto").value(hasItem(DEFAULT_T_2_TIR_PROYECTO)))
            .andExpect(jsonPath("$.[*].t2tirYlb").value(hasItem(DEFAULT_T_2_TIR_YLB)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatosWithEagerRelationshipsIsEnabled() throws Exception {
        when(datosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(datosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(datosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(datosRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDatos() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get the datos
        restDatosMockMvc
            .perform(get(ENTITY_API_URL_ID, datos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datos.getId().intValue()))
            .andExpect(jsonPath("$.inversionTotal").value(DEFAULT_INVERSION_TOTAL))
            .andExpect(jsonPath("$.ingresosxVentas").value(DEFAULT_INGRESOSX_VENTAS))
            .andExpect(jsonPath("$.gananciasYLB").value(DEFAULT_GANANCIAS_YLB))
            .andExpect(jsonPath("$.goubernamentTak").value(DEFAULT_GOUBERNAMENT_TAK))
            .andExpect(jsonPath("$.regalias").value(DEFAULT_REGALIAS))
            .andExpect(jsonPath("$.iue").value(DEFAULT_IUE))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA))
            .andExpect(jsonPath("$.it").value(DEFAULT_IT))
            .andExpect(jsonPath("$.t1precioVentaprom").value(DEFAULT_T_1_PRECIO_VENTAPROM))
            .andExpect(jsonPath("$.t1costoVariable").value(DEFAULT_T_1_COSTO_VARIABLE))
            .andExpect(jsonPath("$.t1costoVartarifa").value(DEFAULT_T_1_COSTO_VARTARIFA))
            .andExpect(jsonPath("$.t1margenUnitario").value(DEFAULT_T_1_MARGEN_UNITARIO))
            .andExpect(jsonPath("$.t1costoFijo").value(DEFAULT_T_1_COSTO_FIJO))
            .andExpect(jsonPath("$.t1costoTotalunitprom").value(DEFAULT_T_1_COSTO_TOTALUNITPROM))
            .andExpect(jsonPath("$.t1puntoEquilibrio").value(DEFAULT_T_1_PUNTO_EQUILIBRIO))
            .andExpect(jsonPath("$.t2tasainteres").value(DEFAULT_T_2_TASAINTERES))
            .andExpect(jsonPath("$.t2tasadescuento").value(DEFAULT_T_2_TASADESCUENTO))
            .andExpect(jsonPath("$.t2vandelProyecto").value(DEFAULT_T_2_VANDEL_PROYECTO))
            .andExpect(jsonPath("$.t2vanYlb").value(DEFAULT_T_2_VAN_YLB))
            .andExpect(jsonPath("$.t2vp").value(DEFAULT_T_2_VP))
            .andExpect(jsonPath("$.t2tirProyecto").value(DEFAULT_T_2_TIR_PROYECTO))
            .andExpect(jsonPath("$.t2tirYlb").value(DEFAULT_T_2_TIR_YLB));
    }

    @Test
    @Transactional
    void getDatosByIdFiltering() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        Long id = datos.getId();

        defaultDatosFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDatosFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDatosFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDatosByInversionTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where inversionTotal equals to
        defaultDatosFiltering("inversionTotal.equals=" + DEFAULT_INVERSION_TOTAL, "inversionTotal.equals=" + UPDATED_INVERSION_TOTAL);
    }

    @Test
    @Transactional
    void getAllDatosByInversionTotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where inversionTotal in
        defaultDatosFiltering(
            "inversionTotal.in=" + DEFAULT_INVERSION_TOTAL + "," + UPDATED_INVERSION_TOTAL,
            "inversionTotal.in=" + UPDATED_INVERSION_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllDatosByInversionTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where inversionTotal is not null
        defaultDatosFiltering("inversionTotal.specified=true", "inversionTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByInversionTotalContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where inversionTotal contains
        defaultDatosFiltering("inversionTotal.contains=" + DEFAULT_INVERSION_TOTAL, "inversionTotal.contains=" + UPDATED_INVERSION_TOTAL);
    }

    @Test
    @Transactional
    void getAllDatosByInversionTotalNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where inversionTotal does not contain
        defaultDatosFiltering(
            "inversionTotal.doesNotContain=" + UPDATED_INVERSION_TOTAL,
            "inversionTotal.doesNotContain=" + DEFAULT_INVERSION_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllDatosByIngresosxVentasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where ingresosxVentas equals to
        defaultDatosFiltering("ingresosxVentas.equals=" + DEFAULT_INGRESOSX_VENTAS, "ingresosxVentas.equals=" + UPDATED_INGRESOSX_VENTAS);
    }

    @Test
    @Transactional
    void getAllDatosByIngresosxVentasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where ingresosxVentas in
        defaultDatosFiltering(
            "ingresosxVentas.in=" + DEFAULT_INGRESOSX_VENTAS + "," + UPDATED_INGRESOSX_VENTAS,
            "ingresosxVentas.in=" + UPDATED_INGRESOSX_VENTAS
        );
    }

    @Test
    @Transactional
    void getAllDatosByIngresosxVentasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where ingresosxVentas is not null
        defaultDatosFiltering("ingresosxVentas.specified=true", "ingresosxVentas.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByIngresosxVentasContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where ingresosxVentas contains
        defaultDatosFiltering(
            "ingresosxVentas.contains=" + DEFAULT_INGRESOSX_VENTAS,
            "ingresosxVentas.contains=" + UPDATED_INGRESOSX_VENTAS
        );
    }

    @Test
    @Transactional
    void getAllDatosByIngresosxVentasNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where ingresosxVentas does not contain
        defaultDatosFiltering(
            "ingresosxVentas.doesNotContain=" + UPDATED_INGRESOSX_VENTAS,
            "ingresosxVentas.doesNotContain=" + DEFAULT_INGRESOSX_VENTAS
        );
    }

    @Test
    @Transactional
    void getAllDatosByGananciasYLBIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where gananciasYLB equals to
        defaultDatosFiltering("gananciasYLB.equals=" + DEFAULT_GANANCIAS_YLB, "gananciasYLB.equals=" + UPDATED_GANANCIAS_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByGananciasYLBIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where gananciasYLB in
        defaultDatosFiltering(
            "gananciasYLB.in=" + DEFAULT_GANANCIAS_YLB + "," + UPDATED_GANANCIAS_YLB,
            "gananciasYLB.in=" + UPDATED_GANANCIAS_YLB
        );
    }

    @Test
    @Transactional
    void getAllDatosByGananciasYLBIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where gananciasYLB is not null
        defaultDatosFiltering("gananciasYLB.specified=true", "gananciasYLB.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByGananciasYLBContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where gananciasYLB contains
        defaultDatosFiltering("gananciasYLB.contains=" + DEFAULT_GANANCIAS_YLB, "gananciasYLB.contains=" + UPDATED_GANANCIAS_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByGananciasYLBNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where gananciasYLB does not contain
        defaultDatosFiltering(
            "gananciasYLB.doesNotContain=" + UPDATED_GANANCIAS_YLB,
            "gananciasYLB.doesNotContain=" + DEFAULT_GANANCIAS_YLB
        );
    }

    @Test
    @Transactional
    void getAllDatosByGoubernamentTakIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where goubernamentTak equals to
        defaultDatosFiltering("goubernamentTak.equals=" + DEFAULT_GOUBERNAMENT_TAK, "goubernamentTak.equals=" + UPDATED_GOUBERNAMENT_TAK);
    }

    @Test
    @Transactional
    void getAllDatosByGoubernamentTakIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where goubernamentTak in
        defaultDatosFiltering(
            "goubernamentTak.in=" + DEFAULT_GOUBERNAMENT_TAK + "," + UPDATED_GOUBERNAMENT_TAK,
            "goubernamentTak.in=" + UPDATED_GOUBERNAMENT_TAK
        );
    }

    @Test
    @Transactional
    void getAllDatosByGoubernamentTakIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where goubernamentTak is not null
        defaultDatosFiltering("goubernamentTak.specified=true", "goubernamentTak.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByGoubernamentTakContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where goubernamentTak contains
        defaultDatosFiltering(
            "goubernamentTak.contains=" + DEFAULT_GOUBERNAMENT_TAK,
            "goubernamentTak.contains=" + UPDATED_GOUBERNAMENT_TAK
        );
    }

    @Test
    @Transactional
    void getAllDatosByGoubernamentTakNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where goubernamentTak does not contain
        defaultDatosFiltering(
            "goubernamentTak.doesNotContain=" + UPDATED_GOUBERNAMENT_TAK,
            "goubernamentTak.doesNotContain=" + DEFAULT_GOUBERNAMENT_TAK
        );
    }

    @Test
    @Transactional
    void getAllDatosByRegaliasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where regalias equals to
        defaultDatosFiltering("regalias.equals=" + DEFAULT_REGALIAS, "regalias.equals=" + UPDATED_REGALIAS);
    }

    @Test
    @Transactional
    void getAllDatosByRegaliasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where regalias in
        defaultDatosFiltering("regalias.in=" + DEFAULT_REGALIAS + "," + UPDATED_REGALIAS, "regalias.in=" + UPDATED_REGALIAS);
    }

    @Test
    @Transactional
    void getAllDatosByRegaliasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where regalias is not null
        defaultDatosFiltering("regalias.specified=true", "regalias.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByRegaliasContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where regalias contains
        defaultDatosFiltering("regalias.contains=" + DEFAULT_REGALIAS, "regalias.contains=" + UPDATED_REGALIAS);
    }

    @Test
    @Transactional
    void getAllDatosByRegaliasNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where regalias does not contain
        defaultDatosFiltering("regalias.doesNotContain=" + UPDATED_REGALIAS, "regalias.doesNotContain=" + DEFAULT_REGALIAS);
    }

    @Test
    @Transactional
    void getAllDatosByIueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iue equals to
        defaultDatosFiltering("iue.equals=" + DEFAULT_IUE, "iue.equals=" + UPDATED_IUE);
    }

    @Test
    @Transactional
    void getAllDatosByIueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iue in
        defaultDatosFiltering("iue.in=" + DEFAULT_IUE + "," + UPDATED_IUE, "iue.in=" + UPDATED_IUE);
    }

    @Test
    @Transactional
    void getAllDatosByIueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iue is not null
        defaultDatosFiltering("iue.specified=true", "iue.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByIueContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iue contains
        defaultDatosFiltering("iue.contains=" + DEFAULT_IUE, "iue.contains=" + UPDATED_IUE);
    }

    @Test
    @Transactional
    void getAllDatosByIueNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iue does not contain
        defaultDatosFiltering("iue.doesNotContain=" + UPDATED_IUE, "iue.doesNotContain=" + DEFAULT_IUE);
    }

    @Test
    @Transactional
    void getAllDatosByIvaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iva equals to
        defaultDatosFiltering("iva.equals=" + DEFAULT_IVA, "iva.equals=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    void getAllDatosByIvaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iva in
        defaultDatosFiltering("iva.in=" + DEFAULT_IVA + "," + UPDATED_IVA, "iva.in=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    void getAllDatosByIvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iva is not null
        defaultDatosFiltering("iva.specified=true", "iva.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByIvaContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iva contains
        defaultDatosFiltering("iva.contains=" + DEFAULT_IVA, "iva.contains=" + UPDATED_IVA);
    }

    @Test
    @Transactional
    void getAllDatosByIvaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where iva does not contain
        defaultDatosFiltering("iva.doesNotContain=" + UPDATED_IVA, "iva.doesNotContain=" + DEFAULT_IVA);
    }

    @Test
    @Transactional
    void getAllDatosByItIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where it equals to
        defaultDatosFiltering("it.equals=" + DEFAULT_IT, "it.equals=" + UPDATED_IT);
    }

    @Test
    @Transactional
    void getAllDatosByItIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where it in
        defaultDatosFiltering("it.in=" + DEFAULT_IT + "," + UPDATED_IT, "it.in=" + UPDATED_IT);
    }

    @Test
    @Transactional
    void getAllDatosByItIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where it is not null
        defaultDatosFiltering("it.specified=true", "it.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByItContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where it contains
        defaultDatosFiltering("it.contains=" + DEFAULT_IT, "it.contains=" + UPDATED_IT);
    }

    @Test
    @Transactional
    void getAllDatosByItNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where it does not contain
        defaultDatosFiltering("it.doesNotContain=" + UPDATED_IT, "it.doesNotContain=" + DEFAULT_IT);
    }

    @Test
    @Transactional
    void getAllDatosByt1precioVentapromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1precioVentaprom equals to
        defaultDatosFiltering(
            "t1precioVentaprom.equals=" + DEFAULT_T_1_PRECIO_VENTAPROM,
            "t1precioVentaprom.equals=" + UPDATED_T_1_PRECIO_VENTAPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1precioVentapromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1precioVentaprom in
        defaultDatosFiltering(
            "t1precioVentaprom.in=" + DEFAULT_T_1_PRECIO_VENTAPROM + "," + UPDATED_T_1_PRECIO_VENTAPROM,
            "t1precioVentaprom.in=" + UPDATED_T_1_PRECIO_VENTAPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1precioVentapromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1precioVentaprom is not null
        defaultDatosFiltering("t1precioVentaprom.specified=true", "t1precioVentaprom.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1precioVentapromContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1precioVentaprom contains
        defaultDatosFiltering(
            "t1precioVentaprom.contains=" + DEFAULT_T_1_PRECIO_VENTAPROM,
            "t1precioVentaprom.contains=" + UPDATED_T_1_PRECIO_VENTAPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1precioVentapromNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1precioVentaprom does not contain
        defaultDatosFiltering(
            "t1precioVentaprom.doesNotContain=" + UPDATED_T_1_PRECIO_VENTAPROM,
            "t1precioVentaprom.doesNotContain=" + DEFAULT_T_1_PRECIO_VENTAPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVariableIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVariable equals to
        defaultDatosFiltering(
            "t1costoVariable.equals=" + DEFAULT_T_1_COSTO_VARIABLE,
            "t1costoVariable.equals=" + UPDATED_T_1_COSTO_VARIABLE
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVariableIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVariable in
        defaultDatosFiltering(
            "t1costoVariable.in=" + DEFAULT_T_1_COSTO_VARIABLE + "," + UPDATED_T_1_COSTO_VARIABLE,
            "t1costoVariable.in=" + UPDATED_T_1_COSTO_VARIABLE
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVariableIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVariable is not null
        defaultDatosFiltering("t1costoVariable.specified=true", "t1costoVariable.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVariableContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVariable contains
        defaultDatosFiltering(
            "t1costoVariable.contains=" + DEFAULT_T_1_COSTO_VARIABLE,
            "t1costoVariable.contains=" + UPDATED_T_1_COSTO_VARIABLE
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVariableNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVariable does not contain
        defaultDatosFiltering(
            "t1costoVariable.doesNotContain=" + UPDATED_T_1_COSTO_VARIABLE,
            "t1costoVariable.doesNotContain=" + DEFAULT_T_1_COSTO_VARIABLE
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVartarifaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVartarifa equals to
        defaultDatosFiltering(
            "t1costoVartarifa.equals=" + DEFAULT_T_1_COSTO_VARTARIFA,
            "t1costoVartarifa.equals=" + UPDATED_T_1_COSTO_VARTARIFA
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVartarifaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVartarifa in
        defaultDatosFiltering(
            "t1costoVartarifa.in=" + DEFAULT_T_1_COSTO_VARTARIFA + "," + UPDATED_T_1_COSTO_VARTARIFA,
            "t1costoVartarifa.in=" + UPDATED_T_1_COSTO_VARTARIFA
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVartarifaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVartarifa is not null
        defaultDatosFiltering("t1costoVartarifa.specified=true", "t1costoVartarifa.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVartarifaContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVartarifa contains
        defaultDatosFiltering(
            "t1costoVartarifa.contains=" + DEFAULT_T_1_COSTO_VARTARIFA,
            "t1costoVartarifa.contains=" + UPDATED_T_1_COSTO_VARTARIFA
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoVartarifaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoVartarifa does not contain
        defaultDatosFiltering(
            "t1costoVartarifa.doesNotContain=" + UPDATED_T_1_COSTO_VARTARIFA,
            "t1costoVartarifa.doesNotContain=" + DEFAULT_T_1_COSTO_VARTARIFA
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1margenUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1margenUnitario equals to
        defaultDatosFiltering(
            "t1margenUnitario.equals=" + DEFAULT_T_1_MARGEN_UNITARIO,
            "t1margenUnitario.equals=" + UPDATED_T_1_MARGEN_UNITARIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1margenUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1margenUnitario in
        defaultDatosFiltering(
            "t1margenUnitario.in=" + DEFAULT_T_1_MARGEN_UNITARIO + "," + UPDATED_T_1_MARGEN_UNITARIO,
            "t1margenUnitario.in=" + UPDATED_T_1_MARGEN_UNITARIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1margenUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1margenUnitario is not null
        defaultDatosFiltering("t1margenUnitario.specified=true", "t1margenUnitario.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1margenUnitarioContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1margenUnitario contains
        defaultDatosFiltering(
            "t1margenUnitario.contains=" + DEFAULT_T_1_MARGEN_UNITARIO,
            "t1margenUnitario.contains=" + UPDATED_T_1_MARGEN_UNITARIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1margenUnitarioNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1margenUnitario does not contain
        defaultDatosFiltering(
            "t1margenUnitario.doesNotContain=" + UPDATED_T_1_MARGEN_UNITARIO,
            "t1margenUnitario.doesNotContain=" + DEFAULT_T_1_MARGEN_UNITARIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoFijoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoFijo equals to
        defaultDatosFiltering("t1costoFijo.equals=" + DEFAULT_T_1_COSTO_FIJO, "t1costoFijo.equals=" + UPDATED_T_1_COSTO_FIJO);
    }

    @Test
    @Transactional
    void getAllDatosByt1costoFijoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoFijo in
        defaultDatosFiltering(
            "t1costoFijo.in=" + DEFAULT_T_1_COSTO_FIJO + "," + UPDATED_T_1_COSTO_FIJO,
            "t1costoFijo.in=" + UPDATED_T_1_COSTO_FIJO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoFijoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoFijo is not null
        defaultDatosFiltering("t1costoFijo.specified=true", "t1costoFijo.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1costoFijoContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoFijo contains
        defaultDatosFiltering("t1costoFijo.contains=" + DEFAULT_T_1_COSTO_FIJO, "t1costoFijo.contains=" + UPDATED_T_1_COSTO_FIJO);
    }

    @Test
    @Transactional
    void getAllDatosByt1costoFijoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoFijo does not contain
        defaultDatosFiltering(
            "t1costoFijo.doesNotContain=" + UPDATED_T_1_COSTO_FIJO,
            "t1costoFijo.doesNotContain=" + DEFAULT_T_1_COSTO_FIJO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoTotalunitpromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoTotalunitprom equals to
        defaultDatosFiltering(
            "t1costoTotalunitprom.equals=" + DEFAULT_T_1_COSTO_TOTALUNITPROM,
            "t1costoTotalunitprom.equals=" + UPDATED_T_1_COSTO_TOTALUNITPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoTotalunitpromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoTotalunitprom in
        defaultDatosFiltering(
            "t1costoTotalunitprom.in=" + DEFAULT_T_1_COSTO_TOTALUNITPROM + "," + UPDATED_T_1_COSTO_TOTALUNITPROM,
            "t1costoTotalunitprom.in=" + UPDATED_T_1_COSTO_TOTALUNITPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoTotalunitpromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoTotalunitprom is not null
        defaultDatosFiltering("t1costoTotalunitprom.specified=true", "t1costoTotalunitprom.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1costoTotalunitpromContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoTotalunitprom contains
        defaultDatosFiltering(
            "t1costoTotalunitprom.contains=" + DEFAULT_T_1_COSTO_TOTALUNITPROM,
            "t1costoTotalunitprom.contains=" + UPDATED_T_1_COSTO_TOTALUNITPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1costoTotalunitpromNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1costoTotalunitprom does not contain
        defaultDatosFiltering(
            "t1costoTotalunitprom.doesNotContain=" + UPDATED_T_1_COSTO_TOTALUNITPROM,
            "t1costoTotalunitprom.doesNotContain=" + DEFAULT_T_1_COSTO_TOTALUNITPROM
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1puntoEquilibrioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1puntoEquilibrio equals to
        defaultDatosFiltering(
            "t1puntoEquilibrio.equals=" + DEFAULT_T_1_PUNTO_EQUILIBRIO,
            "t1puntoEquilibrio.equals=" + UPDATED_T_1_PUNTO_EQUILIBRIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1puntoEquilibrioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1puntoEquilibrio in
        defaultDatosFiltering(
            "t1puntoEquilibrio.in=" + DEFAULT_T_1_PUNTO_EQUILIBRIO + "," + UPDATED_T_1_PUNTO_EQUILIBRIO,
            "t1puntoEquilibrio.in=" + UPDATED_T_1_PUNTO_EQUILIBRIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1puntoEquilibrioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1puntoEquilibrio is not null
        defaultDatosFiltering("t1puntoEquilibrio.specified=true", "t1puntoEquilibrio.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt1puntoEquilibrioContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1puntoEquilibrio contains
        defaultDatosFiltering(
            "t1puntoEquilibrio.contains=" + DEFAULT_T_1_PUNTO_EQUILIBRIO,
            "t1puntoEquilibrio.contains=" + UPDATED_T_1_PUNTO_EQUILIBRIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt1puntoEquilibrioNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t1puntoEquilibrio does not contain
        defaultDatosFiltering(
            "t1puntoEquilibrio.doesNotContain=" + UPDATED_T_1_PUNTO_EQUILIBRIO,
            "t1puntoEquilibrio.doesNotContain=" + DEFAULT_T_1_PUNTO_EQUILIBRIO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tasainteresIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasainteres equals to
        defaultDatosFiltering("t2tasainteres.equals=" + DEFAULT_T_2_TASAINTERES, "t2tasainteres.equals=" + UPDATED_T_2_TASAINTERES);
    }

    @Test
    @Transactional
    void getAllDatosByt2tasainteresIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasainteres in
        defaultDatosFiltering(
            "t2tasainteres.in=" + DEFAULT_T_2_TASAINTERES + "," + UPDATED_T_2_TASAINTERES,
            "t2tasainteres.in=" + UPDATED_T_2_TASAINTERES
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tasainteresIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasainteres is not null
        defaultDatosFiltering("t2tasainteres.specified=true", "t2tasainteres.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2tasainteresContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasainteres contains
        defaultDatosFiltering("t2tasainteres.contains=" + DEFAULT_T_2_TASAINTERES, "t2tasainteres.contains=" + UPDATED_T_2_TASAINTERES);
    }

    @Test
    @Transactional
    void getAllDatosByt2tasainteresNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasainteres does not contain
        defaultDatosFiltering(
            "t2tasainteres.doesNotContain=" + UPDATED_T_2_TASAINTERES,
            "t2tasainteres.doesNotContain=" + DEFAULT_T_2_TASAINTERES
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tasadescuentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasadescuento equals to
        defaultDatosFiltering("t2tasadescuento.equals=" + DEFAULT_T_2_TASADESCUENTO, "t2tasadescuento.equals=" + UPDATED_T_2_TASADESCUENTO);
    }

    @Test
    @Transactional
    void getAllDatosByt2tasadescuentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasadescuento in
        defaultDatosFiltering(
            "t2tasadescuento.in=" + DEFAULT_T_2_TASADESCUENTO + "," + UPDATED_T_2_TASADESCUENTO,
            "t2tasadescuento.in=" + UPDATED_T_2_TASADESCUENTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tasadescuentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasadescuento is not null
        defaultDatosFiltering("t2tasadescuento.specified=true", "t2tasadescuento.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2tasadescuentoContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasadescuento contains
        defaultDatosFiltering(
            "t2tasadescuento.contains=" + DEFAULT_T_2_TASADESCUENTO,
            "t2tasadescuento.contains=" + UPDATED_T_2_TASADESCUENTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tasadescuentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tasadescuento does not contain
        defaultDatosFiltering(
            "t2tasadescuento.doesNotContain=" + UPDATED_T_2_TASADESCUENTO,
            "t2tasadescuento.doesNotContain=" + DEFAULT_T_2_TASADESCUENTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2vandelProyectoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vandelProyecto equals to
        defaultDatosFiltering(
            "t2vandelProyecto.equals=" + DEFAULT_T_2_VANDEL_PROYECTO,
            "t2vandelProyecto.equals=" + UPDATED_T_2_VANDEL_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2vandelProyectoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vandelProyecto in
        defaultDatosFiltering(
            "t2vandelProyecto.in=" + DEFAULT_T_2_VANDEL_PROYECTO + "," + UPDATED_T_2_VANDEL_PROYECTO,
            "t2vandelProyecto.in=" + UPDATED_T_2_VANDEL_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2vandelProyectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vandelProyecto is not null
        defaultDatosFiltering("t2vandelProyecto.specified=true", "t2vandelProyecto.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2vandelProyectoContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vandelProyecto contains
        defaultDatosFiltering(
            "t2vandelProyecto.contains=" + DEFAULT_T_2_VANDEL_PROYECTO,
            "t2vandelProyecto.contains=" + UPDATED_T_2_VANDEL_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2vandelProyectoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vandelProyecto does not contain
        defaultDatosFiltering(
            "t2vandelProyecto.doesNotContain=" + UPDATED_T_2_VANDEL_PROYECTO,
            "t2vandelProyecto.doesNotContain=" + DEFAULT_T_2_VANDEL_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2vanYlbIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vanYlb equals to
        defaultDatosFiltering("t2vanYlb.equals=" + DEFAULT_T_2_VAN_YLB, "t2vanYlb.equals=" + UPDATED_T_2_VAN_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2vanYlbIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vanYlb in
        defaultDatosFiltering("t2vanYlb.in=" + DEFAULT_T_2_VAN_YLB + "," + UPDATED_T_2_VAN_YLB, "t2vanYlb.in=" + UPDATED_T_2_VAN_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2vanYlbIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vanYlb is not null
        defaultDatosFiltering("t2vanYlb.specified=true", "t2vanYlb.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2vanYlbContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vanYlb contains
        defaultDatosFiltering("t2vanYlb.contains=" + DEFAULT_T_2_VAN_YLB, "t2vanYlb.contains=" + UPDATED_T_2_VAN_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2vanYlbNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vanYlb does not contain
        defaultDatosFiltering("t2vanYlb.doesNotContain=" + UPDATED_T_2_VAN_YLB, "t2vanYlb.doesNotContain=" + DEFAULT_T_2_VAN_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2vpIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vp equals to
        defaultDatosFiltering("t2vp.equals=" + DEFAULT_T_2_VP, "t2vp.equals=" + UPDATED_T_2_VP);
    }

    @Test
    @Transactional
    void getAllDatosByt2vpIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vp in
        defaultDatosFiltering("t2vp.in=" + DEFAULT_T_2_VP + "," + UPDATED_T_2_VP, "t2vp.in=" + UPDATED_T_2_VP);
    }

    @Test
    @Transactional
    void getAllDatosByt2vpIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vp is not null
        defaultDatosFiltering("t2vp.specified=true", "t2vp.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2vpContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vp contains
        defaultDatosFiltering("t2vp.contains=" + DEFAULT_T_2_VP, "t2vp.contains=" + UPDATED_T_2_VP);
    }

    @Test
    @Transactional
    void getAllDatosByt2vpNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2vp does not contain
        defaultDatosFiltering("t2vp.doesNotContain=" + UPDATED_T_2_VP, "t2vp.doesNotContain=" + DEFAULT_T_2_VP);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirProyectoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirProyecto equals to
        defaultDatosFiltering("t2tirProyecto.equals=" + DEFAULT_T_2_TIR_PROYECTO, "t2tirProyecto.equals=" + UPDATED_T_2_TIR_PROYECTO);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirProyectoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirProyecto in
        defaultDatosFiltering(
            "t2tirProyecto.in=" + DEFAULT_T_2_TIR_PROYECTO + "," + UPDATED_T_2_TIR_PROYECTO,
            "t2tirProyecto.in=" + UPDATED_T_2_TIR_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tirProyectoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirProyecto is not null
        defaultDatosFiltering("t2tirProyecto.specified=true", "t2tirProyecto.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2tirProyectoContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirProyecto contains
        defaultDatosFiltering("t2tirProyecto.contains=" + DEFAULT_T_2_TIR_PROYECTO, "t2tirProyecto.contains=" + UPDATED_T_2_TIR_PROYECTO);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirProyectoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirProyecto does not contain
        defaultDatosFiltering(
            "t2tirProyecto.doesNotContain=" + UPDATED_T_2_TIR_PROYECTO,
            "t2tirProyecto.doesNotContain=" + DEFAULT_T_2_TIR_PROYECTO
        );
    }

    @Test
    @Transactional
    void getAllDatosByt2tirYlbIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirYlb equals to
        defaultDatosFiltering("t2tirYlb.equals=" + DEFAULT_T_2_TIR_YLB, "t2tirYlb.equals=" + UPDATED_T_2_TIR_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirYlbIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirYlb in
        defaultDatosFiltering("t2tirYlb.in=" + DEFAULT_T_2_TIR_YLB + "," + UPDATED_T_2_TIR_YLB, "t2tirYlb.in=" + UPDATED_T_2_TIR_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirYlbIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirYlb is not null
        defaultDatosFiltering("t2tirYlb.specified=true", "t2tirYlb.specified=false");
    }

    @Test
    @Transactional
    void getAllDatosByt2tirYlbContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirYlb contains
        defaultDatosFiltering("t2tirYlb.contains=" + DEFAULT_T_2_TIR_YLB, "t2tirYlb.contains=" + UPDATED_T_2_TIR_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByt2tirYlbNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        // Get all the datosList where t2tirYlb does not contain
        defaultDatosFiltering("t2tirYlb.doesNotContain=" + UPDATED_T_2_TIR_YLB, "t2tirYlb.doesNotContain=" + DEFAULT_T_2_TIR_YLB);
    }

    @Test
    @Transactional
    void getAllDatosByVersionDatosIsEqualToSomething() throws Exception {
        VersionDatos versionDatos;
        if (TestUtil.findAll(em, VersionDatos.class).isEmpty()) {
            datosRepository.saveAndFlush(datos);
            versionDatos = VersionDatosResourceIT.createEntity();
        } else {
            versionDatos = TestUtil.findAll(em, VersionDatos.class).get(0);
        }
        em.persist(versionDatos);
        em.flush();
        datos.setVersionDatos(versionDatos);
        datosRepository.saveAndFlush(datos);
        Long versionDatosId = versionDatos.getId();
        // Get all the datosList where versionDatos equals to versionDatosId
        defaultDatosShouldBeFound("versionDatosId.equals=" + versionDatosId);

        // Get all the datosList where versionDatos equals to (versionDatosId + 1)
        defaultDatosShouldNotBeFound("versionDatosId.equals=" + (versionDatosId + 1));
    }

    private void defaultDatosFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDatosShouldBeFound(shouldBeFound);
        defaultDatosShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatosShouldBeFound(String filter) throws Exception {
        restDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datos.getId().intValue())))
            .andExpect(jsonPath("$.[*].inversionTotal").value(hasItem(DEFAULT_INVERSION_TOTAL)))
            .andExpect(jsonPath("$.[*].ingresosxVentas").value(hasItem(DEFAULT_INGRESOSX_VENTAS)))
            .andExpect(jsonPath("$.[*].gananciasYLB").value(hasItem(DEFAULT_GANANCIAS_YLB)))
            .andExpect(jsonPath("$.[*].goubernamentTak").value(hasItem(DEFAULT_GOUBERNAMENT_TAK)))
            .andExpect(jsonPath("$.[*].regalias").value(hasItem(DEFAULT_REGALIAS)))
            .andExpect(jsonPath("$.[*].iue").value(hasItem(DEFAULT_IUE)))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA)))
            .andExpect(jsonPath("$.[*].it").value(hasItem(DEFAULT_IT)))
            .andExpect(jsonPath("$.[*].t1precioVentaprom").value(hasItem(DEFAULT_T_1_PRECIO_VENTAPROM)))
            .andExpect(jsonPath("$.[*].t1costoVariable").value(hasItem(DEFAULT_T_1_COSTO_VARIABLE)))
            .andExpect(jsonPath("$.[*].t1costoVartarifa").value(hasItem(DEFAULT_T_1_COSTO_VARTARIFA)))
            .andExpect(jsonPath("$.[*].t1margenUnitario").value(hasItem(DEFAULT_T_1_MARGEN_UNITARIO)))
            .andExpect(jsonPath("$.[*].t1costoFijo").value(hasItem(DEFAULT_T_1_COSTO_FIJO)))
            .andExpect(jsonPath("$.[*].t1costoTotalunitprom").value(hasItem(DEFAULT_T_1_COSTO_TOTALUNITPROM)))
            .andExpect(jsonPath("$.[*].t1puntoEquilibrio").value(hasItem(DEFAULT_T_1_PUNTO_EQUILIBRIO)))
            .andExpect(jsonPath("$.[*].t2tasainteres").value(hasItem(DEFAULT_T_2_TASAINTERES)))
            .andExpect(jsonPath("$.[*].t2tasadescuento").value(hasItem(DEFAULT_T_2_TASADESCUENTO)))
            .andExpect(jsonPath("$.[*].t2vandelProyecto").value(hasItem(DEFAULT_T_2_VANDEL_PROYECTO)))
            .andExpect(jsonPath("$.[*].t2vanYlb").value(hasItem(DEFAULT_T_2_VAN_YLB)))
            .andExpect(jsonPath("$.[*].t2vp").value(hasItem(DEFAULT_T_2_VP)))
            .andExpect(jsonPath("$.[*].t2tirProyecto").value(hasItem(DEFAULT_T_2_TIR_PROYECTO)))
            .andExpect(jsonPath("$.[*].t2tirYlb").value(hasItem(DEFAULT_T_2_TIR_YLB)));

        // Check, that the count call also returns 1
        restDatosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatosShouldNotBeFound(String filter) throws Exception {
        restDatosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDatos() throws Exception {
        // Get the datos
        restDatosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDatos() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datos
        Datos updatedDatos = datosRepository.findById(datos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDatos are not directly saved in db
        em.detach(updatedDatos);
        updatedDatos
            .inversionTotal(UPDATED_INVERSION_TOTAL)
            .ingresosxVentas(UPDATED_INGRESOSX_VENTAS)
            .gananciasYLB(UPDATED_GANANCIAS_YLB)
            .goubernamentTak(UPDATED_GOUBERNAMENT_TAK)
            .regalias(UPDATED_REGALIAS)
            .iue(UPDATED_IUE)
            .iva(UPDATED_IVA)
            .it(UPDATED_IT)
            .t1precioVentaprom(UPDATED_T_1_PRECIO_VENTAPROM)
            .t1costoVariable(UPDATED_T_1_COSTO_VARIABLE)
            .t1costoVartarifa(UPDATED_T_1_COSTO_VARTARIFA)
            .t1margenUnitario(UPDATED_T_1_MARGEN_UNITARIO)
            .t1costoFijo(UPDATED_T_1_COSTO_FIJO)
            .t1costoTotalunitprom(UPDATED_T_1_COSTO_TOTALUNITPROM)
            .t1puntoEquilibrio(UPDATED_T_1_PUNTO_EQUILIBRIO)
            .t2tasainteres(UPDATED_T_2_TASAINTERES)
            .t2tasadescuento(UPDATED_T_2_TASADESCUENTO)
            .t2vandelProyecto(UPDATED_T_2_VANDEL_PROYECTO)
            .t2vanYlb(UPDATED_T_2_VAN_YLB)
            .t2vp(UPDATED_T_2_VP)
            .t2tirProyecto(UPDATED_T_2_TIR_PROYECTO)
            .t2tirYlb(UPDATED_T_2_TIR_YLB);
        DatosDTO datosDTO = datosMapper.toDto(updatedDatos);

        restDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datosDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDatosToMatchAllProperties(updatedDatos);
    }

    @Test
    @Transactional
    void putNonExistingDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datosDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(datosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(datosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatosWithPatch() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datos using partial update
        Datos partialUpdatedDatos = new Datos();
        partialUpdatedDatos.setId(datos.getId());

        partialUpdatedDatos
            .ingresosxVentas(UPDATED_INGRESOSX_VENTAS)
            .gananciasYLB(UPDATED_GANANCIAS_YLB)
            .regalias(UPDATED_REGALIAS)
            .iue(UPDATED_IUE)
            .t1precioVentaprom(UPDATED_T_1_PRECIO_VENTAPROM)
            .t1costoVartarifa(UPDATED_T_1_COSTO_VARTARIFA)
            .t1costoTotalunitprom(UPDATED_T_1_COSTO_TOTALUNITPROM)
            .t2tasadescuento(UPDATED_T_2_TASADESCUENTO)
            .t2vandelProyecto(UPDATED_T_2_VANDEL_PROYECTO)
            .t2vp(UPDATED_T_2_VP)
            .t2tirProyecto(UPDATED_T_2_TIR_PROYECTO);

        restDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatos))
            )
            .andExpect(status().isOk());

        // Validate the Datos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDatos, datos), getPersistedDatos(datos));
    }

    @Test
    @Transactional
    void fullUpdateDatosWithPatch() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the datos using partial update
        Datos partialUpdatedDatos = new Datos();
        partialUpdatedDatos.setId(datos.getId());

        partialUpdatedDatos
            .inversionTotal(UPDATED_INVERSION_TOTAL)
            .ingresosxVentas(UPDATED_INGRESOSX_VENTAS)
            .gananciasYLB(UPDATED_GANANCIAS_YLB)
            .goubernamentTak(UPDATED_GOUBERNAMENT_TAK)
            .regalias(UPDATED_REGALIAS)
            .iue(UPDATED_IUE)
            .iva(UPDATED_IVA)
            .it(UPDATED_IT)
            .t1precioVentaprom(UPDATED_T_1_PRECIO_VENTAPROM)
            .t1costoVariable(UPDATED_T_1_COSTO_VARIABLE)
            .t1costoVartarifa(UPDATED_T_1_COSTO_VARTARIFA)
            .t1margenUnitario(UPDATED_T_1_MARGEN_UNITARIO)
            .t1costoFijo(UPDATED_T_1_COSTO_FIJO)
            .t1costoTotalunitprom(UPDATED_T_1_COSTO_TOTALUNITPROM)
            .t1puntoEquilibrio(UPDATED_T_1_PUNTO_EQUILIBRIO)
            .t2tasainteres(UPDATED_T_2_TASAINTERES)
            .t2tasadescuento(UPDATED_T_2_TASADESCUENTO)
            .t2vandelProyecto(UPDATED_T_2_VANDEL_PROYECTO)
            .t2vanYlb(UPDATED_T_2_VAN_YLB)
            .t2vp(UPDATED_T_2_VP)
            .t2tirProyecto(UPDATED_T_2_TIR_PROYECTO)
            .t2tirYlb(UPDATED_T_2_TIR_YLB);

        restDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDatos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDatos))
            )
            .andExpect(status().isOk());

        // Validate the Datos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatosUpdatableFieldsEquals(partialUpdatedDatos, getPersistedDatos(partialUpdatedDatos));
    }

    @Test
    @Transactional
    void patchNonExistingDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, datosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(datosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDatos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        datos.setId(longCount.incrementAndGet());

        // Create the Datos
        DatosDTO datosDTO = datosMapper.toDto(datos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(datosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Datos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDatos() throws Exception {
        // Initialize the database
        insertedDatos = datosRepository.saveAndFlush(datos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the datos
        restDatosMockMvc
            .perform(delete(ENTITY_API_URL_ID, datos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return datosRepository.count();
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

    protected Datos getPersistedDatos(Datos datos) {
        return datosRepository.findById(datos.getId()).orElseThrow();
    }

    protected void assertPersistedDatosToMatchAllProperties(Datos expectedDatos) {
        assertDatosAllPropertiesEquals(expectedDatos, getPersistedDatos(expectedDatos));
    }

    protected void assertPersistedDatosToMatchUpdatableProperties(Datos expectedDatos) {
        assertDatosAllUpdatablePropertiesEquals(expectedDatos, getPersistedDatos(expectedDatos));
    }
}
