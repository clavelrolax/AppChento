package bo.ylb.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DatosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Datos getDatosSample1() {
        return new Datos()
            .id(1L)
            .inversionTotal("inversionTotal1")
            .ingresosxVentas("ingresosxVentas1")
            .gananciasYLB("gananciasYLB1")
            .goubernamentTak("goubernamentTak1")
            .regalias("regalias1")
            .iue("iue1")
            .iva("iva1")
            .it("it1")
            .t1precioVentaprom("t1precioVentaprom1")
            .t1costoVariable("t1costoVariable1")
            .t1costoVartarifa("t1costoVartarifa1")
            .t1margenUnitario("t1margenUnitario1")
            .t1costoFijo("t1costoFijo1")
            .t1costoTotalunitprom("t1costoTotalunitprom1")
            .t1puntoEquilibrio("t1puntoEquilibrio1")
            .t2tasainteres("t2tasainteres1")
            .t2tasadescuento("t2tasadescuento1")
            .t2vandelProyecto("t2vandelProyecto1")
            .t2vanYlb("t2vanYlb1")
            .t2vp("t2vp1")
            .t2tirProyecto("t2tirProyecto1")
            .t2tirYlb("t2tirYlb1");
    }

    public static Datos getDatosSample2() {
        return new Datos()
            .id(2L)
            .inversionTotal("inversionTotal2")
            .ingresosxVentas("ingresosxVentas2")
            .gananciasYLB("gananciasYLB2")
            .goubernamentTak("goubernamentTak2")
            .regalias("regalias2")
            .iue("iue2")
            .iva("iva2")
            .it("it2")
            .t1precioVentaprom("t1precioVentaprom2")
            .t1costoVariable("t1costoVariable2")
            .t1costoVartarifa("t1costoVartarifa2")
            .t1margenUnitario("t1margenUnitario2")
            .t1costoFijo("t1costoFijo2")
            .t1costoTotalunitprom("t1costoTotalunitprom2")
            .t1puntoEquilibrio("t1puntoEquilibrio2")
            .t2tasainteres("t2tasainteres2")
            .t2tasadescuento("t2tasadescuento2")
            .t2vandelProyecto("t2vandelProyecto2")
            .t2vanYlb("t2vanYlb2")
            .t2vp("t2vp2")
            .t2tirProyecto("t2tirProyecto2")
            .t2tirYlb("t2tirYlb2");
    }

    public static Datos getDatosRandomSampleGenerator() {
        return new Datos()
            .id(longCount.incrementAndGet())
            .inversionTotal(UUID.randomUUID().toString())
            .ingresosxVentas(UUID.randomUUID().toString())
            .gananciasYLB(UUID.randomUUID().toString())
            .goubernamentTak(UUID.randomUUID().toString())
            .regalias(UUID.randomUUID().toString())
            .iue(UUID.randomUUID().toString())
            .iva(UUID.randomUUID().toString())
            .it(UUID.randomUUID().toString())
            .t1precioVentaprom(UUID.randomUUID().toString())
            .t1costoVariable(UUID.randomUUID().toString())
            .t1costoVartarifa(UUID.randomUUID().toString())
            .t1margenUnitario(UUID.randomUUID().toString())
            .t1costoFijo(UUID.randomUUID().toString())
            .t1costoTotalunitprom(UUID.randomUUID().toString())
            .t1puntoEquilibrio(UUID.randomUUID().toString())
            .t2tasainteres(UUID.randomUUID().toString())
            .t2tasadescuento(UUID.randomUUID().toString())
            .t2vandelProyecto(UUID.randomUUID().toString())
            .t2vanYlb(UUID.randomUUID().toString())
            .t2vp(UUID.randomUUID().toString())
            .t2tirProyecto(UUID.randomUUID().toString())
            .t2tirYlb(UUID.randomUUID().toString());
    }
}
