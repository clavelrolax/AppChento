package bo.ylb.app.web.rest;

import static bo.ylb.app.domain.OperadorAsserts.*;
import static bo.ylb.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bo.ylb.app.IntegrationTest;
import bo.ylb.app.domain.Operador;
import bo.ylb.app.repository.OperadorRepository;
import bo.ylb.app.service.dto.OperadorDTO;
import bo.ylb.app.service.mapper.OperadorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperadorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_CREACION = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/operadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private OperadorMapper operadorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperadorMockMvc;

    private Operador operador;

    private Operador insertedOperador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operador createEntity() {
        return new Operador().nombre(DEFAULT_NOMBRE).nacionalidad(DEFAULT_NACIONALIDAD).fechaCreacion(DEFAULT_FECHA_CREACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operador createUpdatedEntity() {
        return new Operador().nombre(UPDATED_NOMBRE).nacionalidad(UPDATED_NACIONALIDAD).fechaCreacion(UPDATED_FECHA_CREACION);
    }

    @BeforeEach
    public void initTest() {
        operador = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOperador != null) {
            operadorRepository.delete(insertedOperador);
            insertedOperador = null;
        }
    }

    @Test
    @Transactional
    void createOperador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);
        var returnedOperadorDTO = om.readValue(
            restOperadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OperadorDTO.class
        );

        // Validate the Operador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOperador = operadorMapper.toEntity(returnedOperadorDTO);
        assertOperadorUpdatableFieldsEquals(returnedOperador, getPersistedOperador(returnedOperador));

        insertedOperador = returnedOperador;
    }

    @Test
    @Transactional
    void createOperadorWithExistingId() throws Exception {
        // Create the Operador with an existing ID
        operador.setId(1L);
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operador.setNombre(null);

        // Create the Operador, which fails.
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        restOperadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNacionalidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operador.setNacionalidad(null);

        // Create the Operador, which fails.
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        restOperadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaCreacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        operador.setFechaCreacion(null);

        // Create the Operador, which fails.
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        restOperadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOperadors() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())));
    }

    @Test
    @Transactional
    void getOperador() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get the operador
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL_ID, operador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()));
    }

    @Test
    @Transactional
    void getOperadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        Long id = operador.getId();

        defaultOperadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultOperadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultOperadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOperadorsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nombre equals to
        defaultOperadorFiltering("nombre.equals=" + DEFAULT_NOMBRE, "nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllOperadorsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nombre in
        defaultOperadorFiltering("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE, "nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllOperadorsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nombre is not null
        defaultOperadorFiltering("nombre.specified=true", "nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllOperadorsByNombreContainsSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nombre contains
        defaultOperadorFiltering("nombre.contains=" + DEFAULT_NOMBRE, "nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllOperadorsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nombre does not contain
        defaultOperadorFiltering("nombre.doesNotContain=" + UPDATED_NOMBRE, "nombre.doesNotContain=" + DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void getAllOperadorsByNacionalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nacionalidad equals to
        defaultOperadorFiltering("nacionalidad.equals=" + DEFAULT_NACIONALIDAD, "nacionalidad.equals=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    void getAllOperadorsByNacionalidadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nacionalidad in
        defaultOperadorFiltering(
            "nacionalidad.in=" + DEFAULT_NACIONALIDAD + "," + UPDATED_NACIONALIDAD,
            "nacionalidad.in=" + UPDATED_NACIONALIDAD
        );
    }

    @Test
    @Transactional
    void getAllOperadorsByNacionalidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nacionalidad is not null
        defaultOperadorFiltering("nacionalidad.specified=true", "nacionalidad.specified=false");
    }

    @Test
    @Transactional
    void getAllOperadorsByNacionalidadContainsSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nacionalidad contains
        defaultOperadorFiltering("nacionalidad.contains=" + DEFAULT_NACIONALIDAD, "nacionalidad.contains=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    void getAllOperadorsByNacionalidadNotContainsSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where nacionalidad does not contain
        defaultOperadorFiltering(
            "nacionalidad.doesNotContain=" + UPDATED_NACIONALIDAD,
            "nacionalidad.doesNotContain=" + DEFAULT_NACIONALIDAD
        );
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion equals to
        defaultOperadorFiltering("fechaCreacion.equals=" + DEFAULT_FECHA_CREACION, "fechaCreacion.equals=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion in
        defaultOperadorFiltering(
            "fechaCreacion.in=" + DEFAULT_FECHA_CREACION + "," + UPDATED_FECHA_CREACION,
            "fechaCreacion.in=" + UPDATED_FECHA_CREACION
        );
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion is not null
        defaultOperadorFiltering("fechaCreacion.specified=true", "fechaCreacion.specified=false");
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion is greater than or equal to
        defaultOperadorFiltering(
            "fechaCreacion.greaterThanOrEqual=" + DEFAULT_FECHA_CREACION,
            "fechaCreacion.greaterThanOrEqual=" + UPDATED_FECHA_CREACION
        );
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion is less than or equal to
        defaultOperadorFiltering(
            "fechaCreacion.lessThanOrEqual=" + DEFAULT_FECHA_CREACION,
            "fechaCreacion.lessThanOrEqual=" + SMALLER_FECHA_CREACION
        );
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion is less than
        defaultOperadorFiltering("fechaCreacion.lessThan=" + UPDATED_FECHA_CREACION, "fechaCreacion.lessThan=" + DEFAULT_FECHA_CREACION);
    }

    @Test
    @Transactional
    void getAllOperadorsByFechaCreacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        // Get all the operadorList where fechaCreacion is greater than
        defaultOperadorFiltering(
            "fechaCreacion.greaterThan=" + SMALLER_FECHA_CREACION,
            "fechaCreacion.greaterThan=" + DEFAULT_FECHA_CREACION
        );
    }

    private void defaultOperadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultOperadorShouldBeFound(shouldBeFound);
        defaultOperadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOperadorShouldBeFound(String filter) throws Exception {
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())));

        // Check, that the count call also returns 1
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOperadorShouldNotBeFound(String filter) throws Exception {
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOperador() throws Exception {
        // Get the operador
        restOperadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperador() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operador
        Operador updatedOperador = operadorRepository.findById(operador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOperador are not directly saved in db
        em.detach(updatedOperador);
        updatedOperador.nombre(UPDATED_NOMBRE).nacionalidad(UPDATED_NACIONALIDAD).fechaCreacion(UPDATED_FECHA_CREACION);
        OperadorDTO operadorDTO = operadorMapper.toDto(updatedOperador);

        restOperadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOperadorToMatchAllProperties(updatedOperador);
    }

    @Test
    @Transactional
    void putNonExistingOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(operadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperadorWithPatch() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operador using partial update
        Operador partialUpdatedOperador = new Operador();
        partialUpdatedOperador.setId(operador.getId());

        partialUpdatedOperador.nombre(UPDATED_NOMBRE).nacionalidad(UPDATED_NACIONALIDAD).fechaCreacion(UPDATED_FECHA_CREACION);

        restOperadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperador))
            )
            .andExpect(status().isOk());

        // Validate the Operador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperadorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOperador, operador), getPersistedOperador(operador));
    }

    @Test
    @Transactional
    void fullUpdateOperadorWithPatch() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the operador using partial update
        Operador partialUpdatedOperador = new Operador();
        partialUpdatedOperador.setId(operador.getId());

        partialUpdatedOperador.nombre(UPDATED_NOMBRE).nacionalidad(UPDATED_NACIONALIDAD).fechaCreacion(UPDATED_FECHA_CREACION);

        restOperadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOperador))
            )
            .andExpect(status().isOk());

        // Validate the Operador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOperadorUpdatableFieldsEquals(partialUpdatedOperador, getPersistedOperador(partialUpdatedOperador));
    }

    @Test
    @Transactional
    void patchNonExistingOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(operadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        operador.setId(longCount.incrementAndGet());

        // Create the Operador
        OperadorDTO operadorDTO = operadorMapper.toDto(operador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(operadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Operador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperador() throws Exception {
        // Initialize the database
        insertedOperador = operadorRepository.saveAndFlush(operador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the operador
        restOperadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, operador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return operadorRepository.count();
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

    protected Operador getPersistedOperador(Operador operador) {
        return operadorRepository.findById(operador.getId()).orElseThrow();
    }

    protected void assertPersistedOperadorToMatchAllProperties(Operador expectedOperador) {
        assertOperadorAllPropertiesEquals(expectedOperador, getPersistedOperador(expectedOperador));
    }

    protected void assertPersistedOperadorToMatchUpdatableProperties(Operador expectedOperador) {
        assertOperadorAllUpdatablePropertiesEquals(expectedOperador, getPersistedOperador(expectedOperador));
    }
}
