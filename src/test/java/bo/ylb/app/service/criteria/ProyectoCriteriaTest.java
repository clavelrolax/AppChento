package bo.ylb.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProyectoCriteriaTest {

    @Test
    void newProyectoCriteriaHasAllFiltersNullTest() {
        var proyectoCriteria = new ProyectoCriteria();
        assertThat(proyectoCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void proyectoCriteriaFluentMethodsCreatesFiltersTest() {
        var proyectoCriteria = new ProyectoCriteria();

        setAllFilters(proyectoCriteria);

        assertThat(proyectoCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void proyectoCriteriaCopyCreatesNullFilterTest() {
        var proyectoCriteria = new ProyectoCriteria();
        var copy = proyectoCriteria.copy();

        assertThat(proyectoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(proyectoCriteria)
        );
    }

    @Test
    void proyectoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var proyectoCriteria = new ProyectoCriteria();
        setAllFilters(proyectoCriteria);

        var copy = proyectoCriteria.copy();

        assertThat(proyectoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(proyectoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var proyectoCriteria = new ProyectoCriteria();

        assertThat(proyectoCriteria).hasToString("ProyectoCriteria{}");
    }

    private static void setAllFilters(ProyectoCriteria proyectoCriteria) {
        proyectoCriteria.id();
        proyectoCriteria.nombreProyecto();
        proyectoCriteria.objetivo();
        proyectoCriteria.tiempoProyecto();
        proyectoCriteria.fechaInicio();
        proyectoCriteria.fechaFin();
        proyectoCriteria.operadorId();
        proyectoCriteria.versionDatosId();
        proyectoCriteria.distinct();
    }

    private static Condition<ProyectoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNombreProyecto()) &&
                condition.apply(criteria.getObjetivo()) &&
                condition.apply(criteria.getTiempoProyecto()) &&
                condition.apply(criteria.getFechaInicio()) &&
                condition.apply(criteria.getFechaFin()) &&
                condition.apply(criteria.getOperadorId()) &&
                condition.apply(criteria.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProyectoCriteria> copyFiltersAre(ProyectoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNombreProyecto(), copy.getNombreProyecto()) &&
                condition.apply(criteria.getObjetivo(), copy.getObjetivo()) &&
                condition.apply(criteria.getTiempoProyecto(), copy.getTiempoProyecto()) &&
                condition.apply(criteria.getFechaInicio(), copy.getFechaInicio()) &&
                condition.apply(criteria.getFechaFin(), copy.getFechaFin()) &&
                condition.apply(criteria.getOperadorId(), copy.getOperadorId()) &&
                condition.apply(criteria.getVersionDatosId(), copy.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
