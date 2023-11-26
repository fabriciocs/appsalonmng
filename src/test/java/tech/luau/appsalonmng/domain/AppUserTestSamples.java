package tech.luau.appsalonmng.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser().id(1L).username("username1").password("password1").email("email1").role("role1");
    }

    public static AppUser getAppUserSample2() {
        return new AppUser().id(2L).username("username2").password("password2").email("email2").role("role2");
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser()
            .id(longCount.incrementAndGet())
            .username(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .role(UUID.randomUUID().toString());
    }
}
