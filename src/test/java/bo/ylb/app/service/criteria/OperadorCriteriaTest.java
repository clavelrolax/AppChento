package bo.ylb.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OperadorCriteriaTest {

    @Test
    void newOperadorCriteriaHasAllFiltersNullTest() {
        var operadorCriteria = new OperadorCriteria();
        assertThat(operadorCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void operadorCriteriaFluentMethodsCreatesFiltersTest() {
        var operadorCriteria = new OperadorCriteria();

        setAllFilters(operadorCriteria);

        assertThat(operadorCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void operadorCriteriaCopyCreatesNullFilterTest() {
        var operadorCriteria = new OperadorCriteria();
        var copy = operadorCriteria.copy();

        assertThat(operadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(operadorCriteria)
        );
    }

    @Test
    void operadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var operadorCriteria = new OperadorCriteria();
        setAllFilters(operadorCriteria);

        var copy = operadorCriteria.copy();

        assertThat(operadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(operadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var operadorCriteria = new OperadorCriteria();

        assertThat(operadorCriteria).hasToString("OperadorCriteria{}");
    }

    private static void setAllFilters(OperadorCriteria operadorCriteria) {
        operadorCriteria.id();
        operadorCriteria.nombre();
        operadorCriteria.nacionalidad();
        operadorCriteria.fechaCreacion();
        operadorCriteria.proyectoId();
        operadorCriteria.distinct();
    }

    private static Condition<OperadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNombre()) &&
                condition.apply(criteria.getNacionalidad()) &&
                condition.apply(criteria.getFechaCreacion()) &&
                condition.apply(criteria.getProyectoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<OperadorCriteria> copyFiltersAre(OperadorCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNombre(), copy.getNombre()) &&
                condition.apply(criteria.getNacionalidad(), copy.getNacionalidad()) &&
                condition.apply(criteria.getFechaCreacion(), copy.getFechaCreacion()) &&
                condition.apply(criteria.getProyectoId(), copy.getProyectoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
