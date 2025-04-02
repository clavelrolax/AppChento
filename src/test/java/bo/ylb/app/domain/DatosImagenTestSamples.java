package bo.ylb.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DatosImagenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DatosImagen getDatosImagenSample1() {
        return new DatosImagen().id(1L);
    }

    public static DatosImagen getDatosImagenSample2() {
        return new DatosImagen().id(2L);
    }

    public static DatosImagen getDatosImagenRandomSampleGenerator() {
        return new DatosImagen().id(longCount.incrementAndGet());
    }
}
