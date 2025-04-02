package bo.ylb.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DatosCriteriaTest {

    @Test
    void newDatosCriteriaHasAllFiltersNullTest() {
        var datosCriteria = new DatosCriteria();
        assertThat(datosCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void datosCriteriaFluentMethodsCreatesFiltersTest() {
        var datosCriteria = new DatosCriteria();

        setAllFilters(datosCriteria);

        assertThat(datosCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void datosCriteriaCopyCreatesNullFilterTest() {
        var datosCriteria = new DatosCriteria();
        var copy = datosCriteria.copy();

        assertThat(datosCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(datosCriteria)
        );
    }

    @Test
    void datosCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var datosCriteria = new DatosCriteria();
        setAllFilters(datosCriteria);

        var copy = datosCriteria.copy();

        assertThat(datosCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(datosCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var datosCriteria = new DatosCriteria();

        assertThat(datosCriteria).hasToString("DatosCriteria{}");
    }

    private static void setAllFilters(DatosCriteria datosCriteria) {
        datosCriteria.id();
        datosCriteria.inversionTotal();
        datosCriteria.ingresosxVentas();
        datosCriteria.gananciasYLB();
        datosCriteria.goubernamentTak();
        datosCriteria.regalias();
        datosCriteria.iue();
        datosCriteria.iva();
        datosCriteria.it();
        datosCriteria.t1precioVentaprom();
        datosCriteria.t1costoVariable();
        datosCriteria.t1costoVartarifa();
        datosCriteria.t1margenUnitario();
        datosCriteria.t1costoFijo();
        datosCriteria.t1costoTotalunitprom();
        datosCriteria.t1puntoEquilibrio();
        datosCriteria.t2tasainteres();
        datosCriteria.t2tasadescuento();
        datosCriteria.t2vandelProyecto();
        datosCriteria.t2vanYlb();
        datosCriteria.t2vp();
        datosCriteria.t2tirProyecto();
        datosCriteria.t2tirYlb();
        datosCriteria.versionDatosId();
        datosCriteria.distinct();
    }

    private static Condition<DatosCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInversionTotal()) &&
                condition.apply(criteria.getIngresosxVentas()) &&
                condition.apply(criteria.getGananciasYLB()) &&
                condition.apply(criteria.getGoubernamentTak()) &&
                condition.apply(criteria.getRegalias()) &&
                condition.apply(criteria.getIue()) &&
                condition.apply(criteria.getIva()) &&
                condition.apply(criteria.getIt()) &&
                condition.apply(criteria.gett1precioVentaprom()) &&
                condition.apply(criteria.gett1costoVariable()) &&
                condition.apply(criteria.gett1costoVartarifa()) &&
                condition.apply(criteria.gett1margenUnitario()) &&
                condition.apply(criteria.gett1costoFijo()) &&
                condition.apply(criteria.gett1costoTotalunitprom()) &&
                condition.apply(criteria.gett1puntoEquilibrio()) &&
                condition.apply(criteria.gett2tasainteres()) &&
                condition.apply(criteria.gett2tasadescuento()) &&
                condition.apply(criteria.gett2vandelProyecto()) &&
                condition.apply(criteria.gett2vanYlb()) &&
                condition.apply(criteria.gett2vp()) &&
                condition.apply(criteria.gett2tirProyecto()) &&
                condition.apply(criteria.gett2tirYlb()) &&
                condition.apply(criteria.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DatosCriteria> copyFiltersAre(DatosCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInversionTotal(), copy.getInversionTotal()) &&
                condition.apply(criteria.getIngresosxVentas(), copy.getIngresosxVentas()) &&
                condition.apply(criteria.getGananciasYLB(), copy.getGananciasYLB()) &&
                condition.apply(criteria.getGoubernamentTak(), copy.getGoubernamentTak()) &&
                condition.apply(criteria.getRegalias(), copy.getRegalias()) &&
                condition.apply(criteria.getIue(), copy.getIue()) &&
                condition.apply(criteria.getIva(), copy.getIva()) &&
                condition.apply(criteria.getIt(), copy.getIt()) &&
                condition.apply(criteria.gett1precioVentaprom(), copy.gett1precioVentaprom()) &&
                condition.apply(criteria.gett1costoVariable(), copy.gett1costoVariable()) &&
                condition.apply(criteria.gett1costoVartarifa(), copy.gett1costoVartarifa()) &&
                condition.apply(criteria.gett1margenUnitario(), copy.gett1margenUnitario()) &&
                condition.apply(criteria.gett1costoFijo(), copy.gett1costoFijo()) &&
                condition.apply(criteria.gett1costoTotalunitprom(), copy.gett1costoTotalunitprom()) &&
                condition.apply(criteria.gett1puntoEquilibrio(), copy.gett1puntoEquilibrio()) &&
                condition.apply(criteria.gett2tasainteres(), copy.gett2tasainteres()) &&
                condition.apply(criteria.gett2tasadescuento(), copy.gett2tasadescuento()) &&
                condition.apply(criteria.gett2vandelProyecto(), copy.gett2vandelProyecto()) &&
                condition.apply(criteria.gett2vanYlb(), copy.gett2vanYlb()) &&
                condition.apply(criteria.gett2vp(), copy.gett2vp()) &&
                condition.apply(criteria.gett2tirProyecto(), copy.gett2tirProyecto()) &&
                condition.apply(criteria.gett2tirYlb(), copy.gett2tirYlb()) &&
                condition.apply(criteria.getVersionDatosId(), copy.getVersionDatosId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
