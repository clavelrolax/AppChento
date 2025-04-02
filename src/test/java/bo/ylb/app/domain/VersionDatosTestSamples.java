package bo.ylb.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VersionDatosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static VersionDatos getVersionDatosSample1() {
        return new VersionDatos().id(1L).nombreVersion("nombreVersion1").citeVersion("citeVersion1");
    }

    public static VersionDatos getVersionDatosSample2() {
        return new VersionDatos().id(2L).nombreVersion("nombreVersion2").citeVersion("citeVersion2");
    }

    public static VersionDatos getVersionDatosRandomSampleGenerator() {
        return new VersionDatos()
            .id(longCount.incrementAndGet())
            .nombreVersion(UUID.randomUUID().toString())
            .citeVersion(UUID.randomUUID().toString());
    }
}
