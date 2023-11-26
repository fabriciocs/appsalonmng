package tech.luau.appsalonmng.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StockHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static StockHistory getStockHistorySample1() {
        return new StockHistory().id(1L).quantityChanged(1).reason("reason1");
    }

    public static StockHistory getStockHistorySample2() {
        return new StockHistory().id(2L).quantityChanged(2).reason("reason2");
    }

    public static StockHistory getStockHistoryRandomSampleGenerator() {
        return new StockHistory()
            .id(longCount.incrementAndGet())
            .quantityChanged(intCount.incrementAndGet())
            .reason(UUID.randomUUID().toString());
    }
}
