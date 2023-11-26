package tech.luau.appsalonmng.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class FinancialTransactionMapperTest {

    private FinancialTransactionMapper financialTransactionMapper;

    @BeforeEach
    public void setUp() {
        financialTransactionMapper = new FinancialTransactionMapperImpl();
    }
}
