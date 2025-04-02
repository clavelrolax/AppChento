package bo.ylb.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProyectoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProyectoAllPropertiesEquals(Proyecto expected, Proyecto actual) {
        assertProyectoAutoGeneratedPropertiesEquals(expected, actual);
        assertProyectoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProyectoAllUpdatablePropertiesEquals(Proyecto expected, Proyecto actual) {
        assertProyectoUpdatableFieldsEquals(expected, actual);
        assertProyectoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProyectoAutoGeneratedPropertiesEquals(Proyecto expected, Proyecto actual) {
        assertThat(actual)
            .as("Verify Proyecto auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProyectoUpdatableFieldsEquals(Proyecto expected, Proyecto actual) {
        assertThat(actual)
            .as("Verify Proyecto relevant properties")
            .satisfies(a -> assertThat(a.getNombreProyecto()).as("check nombreProyecto").isEqualTo(expected.getNombreProyecto()))
            .satisfies(a -> assertThat(a.getObjetivo()).as("check objetivo").isEqualTo(expected.getObjetivo()))
            .satisfies(a -> assertThat(a.getTiempoProyecto()).as("check tiempoProyecto").isEqualTo(expected.getTiempoProyecto()))
            .satisfies(a -> assertThat(a.getFechaInicio()).as("check fechaInicio").isEqualTo(expected.getFechaInicio()))
            .satisfies(a -> assertThat(a.getFechaFin()).as("check fechaFin").isEqualTo(expected.getFechaFin()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProyectoUpdatableRelationshipsEquals(Proyecto expected, Proyecto actual) {
        assertThat(actual)
            .as("Verify Proyecto relationships")
            .satisfies(a -> assertThat(a.getOperador()).as("check operador").isEqualTo(expected.getOperador()));
    }
}
