package bo.ylb.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OperadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Operador getOperadorSample1() {
        return new Operador().id(1L).nombre("nombre1").nacionalidad("nacionalidad1");
    }

    public static Operador getOperadorSample2() {
        return new Operador().id(2L).nombre("nombre2").nacionalidad("nacionalidad2");
    }

    public static Operador getOperadorRandomSampleGenerator() {
        return new Operador()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .nacionalidad(UUID.randomUUID().toString());
    }
}
