package bo.ylb.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DatosImagenCriteriaTest {

    @Test
    void newDatosImagenCriteriaHasAllFiltersNullTest() {
        var datosImagenCriteria = new DatosImagenCriteria();
        assertThat(datosImagenCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void datosImagenCriteriaFluentMethodsCreatesFiltersTest() {
        var datosImagenCriteria = new DatosImagenCriteria();

        setAllFilters(datosImagenCriteria);

        assertThat(datosImagenCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void datosImagenCriteriaCopyCreatesNullFilterTest() {
        var datosImagenCriteria = new DatosImagenCriteria();
        var copy = datosImagenCriteria.copy();

        assertThat(datosImagenCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(datosImagenCriteria)
        );
    }

    @Test
    void datosImagenCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var datosImagenCriteria = new DatosImagenCriteria();
        setAllFilters(datosImagenCriteria);

        var copy = datosImagenCriteria.copy();

        assertThat(datosImagenCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(datosImagenCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var datosImagenCriteria = new DatosImagenCriteria();

        assertThat(datosImagenCriteria).hasToString("DatosImagenCriteria{}");
    }

    private static void setAllFilters(DatosImagenCriteria datosImagenCriteria) {
        datosImagenCriteria.id();
        datosImagenCriteria.versionDatosId();
        datosImagenCriteria.distinct();
    }

    private static Condition<DatosImagenCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DatosImagenCriteria> copyFiltersAre(DatosImagenCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getVersionDatosId(), copy.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
