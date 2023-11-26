package tech.luau.appsalonmng.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RewardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Reward getRewardSample1() {
        return new Reward().id(1L).points(1);
    }

    public static Reward getRewardSample2() {
        return new Reward().id(2L).points(2);
    }

    public static Reward getRewardRandomSampleGenerator() {
        return new Reward().id(longCount.incrementAndGet()).points(intCount.incrementAndGet());
    }
}
