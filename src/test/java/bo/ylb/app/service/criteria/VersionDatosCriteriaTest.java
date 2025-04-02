package bo.ylb.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VersionDatosCriteriaTest {

    @Test
    void newVersionDatosCriteriaHasAllFiltersNullTest() {
        var versionDatosCriteria = new VersionDatosCriteria();
        assertThat(versionDatosCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void versionDatosCriteriaFluentMethodsCreatesFiltersTest() {
        var versionDatosCriteria = new VersionDatosCriteria();

        setAllFilters(versionDatosCriteria);

        assertThat(versionDatosCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void versionDatosCriteriaCopyCreatesNullFilterTest() {
        var versionDatosCriteria = new VersionDatosCriteria();
        var copy = versionDatosCriteria.copy();

        assertThat(versionDatosCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(versionDatosCriteria)
        );
    }

    @Test
    void versionDatosCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var versionDatosCriteria = new VersionDatosCriteria();
        setAllFilters(versionDatosCriteria);

        var copy = versionDatosCriteria.copy();

        assertThat(versionDatosCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(versionDatosCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var versionDatosCriteria = new VersionDatosCriteria();

        assertThat(versionDatosCriteria).hasToString("VersionDatosCriteria{}");
    }

    private static void setAllFilters(VersionDatosCriteria versionDatosCriteria) {
        versionDatosCriteria.id();
        versionDatosCriteria.nombreVersion();
        versionDatosCriteria.fechaVersion();
        versionDatosCriteria.citeVersion();
        versionDatosCriteria.proyectoId();
        versionDatosCriteria.datosId();
        versionDatosCriteria.datosImagenId();
        versionDatosCriteria.distinct();
    }

    private static Condition<VersionDatosCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNombreVersion()) &&
                condition.apply(criteria.getFechaVersion()) &&
                condition.apply(criteria.getCiteVersion()) &&
                condition.apply(criteria.getProyectoId()) &&
                condition.apply(criteria.getDatosId()) &&
                condition.apply(criteria.getDatosImagenId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VersionDatosCriteria> copyFiltersAre(
        VersionDatosCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNombreVersion(), copy.getNombreVersion()) &&
                condition.apply(criteria.getFechaVersion(), copy.getFechaVersion()) &&
                condition.apply(criteria.getCiteVersion(), copy.getCiteVersion()) &&
                condition.apply(criteria.getProyectoId(), copy.getProyectoId()) &&
                condition.apply(criteria.getDatosId(), copy.getDatosId()) &&
                condition.apply(criteria.getDatosImagenId(), copy.getDatosImagenId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
