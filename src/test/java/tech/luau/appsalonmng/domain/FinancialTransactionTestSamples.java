package tech.luau.appsalonmng.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FinancialTransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FinancialTransaction getFinancialTransactionSample1() {
        return new FinancialTransaction().id(1L).transactionType("transactionType1");
    }

    public static FinancialTransaction getFinancialTransactionSample2() {
        return new FinancialTransaction().id(2L).transactionType("transactionType2");
    }

    public static FinancialTransaction getFinancialTransactionRandomSampleGenerator() {
        return new FinancialTransaction().id(longCount.incrementAndGet()).transactionType(UUID.randomUUID().toString());
    }
}
