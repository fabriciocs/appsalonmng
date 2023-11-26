package tech.luau.appsalonmng.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class StockHistoryMapperTest {

    private StockHistoryMapper stockHistoryMapper;

    @BeforeEach
    public void setUp() {
        stockHistoryMapper = new StockHistoryMapperImpl();
    }
}
