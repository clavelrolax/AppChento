package bo.ylb.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProyectoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Proyecto getProyectoSample1() {
        return new Proyecto().id(1L).nombreProyecto("nombreProyecto1").objetivo("objetivo1").tiempoProyecto(1);
    }

    public static Proyecto getProyectoSample2() {
        return new Proyecto().id(2L).nombreProyecto("nombreProyecto2").objetivo("objetivo2").tiempoProyecto(2);
    }

    public static Proyecto getProyectoRandomSampleGenerator() {
        return new Proyecto()
            .id(longCount.incrementAndGet())
            .nombreProyecto(UUID.randomUUID().toString())
            .objetivo(UUID.randomUUID().toString())
            .tiempoProyecto(intCount.incrementAndGet());
    }
}
