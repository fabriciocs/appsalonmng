package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.ProductTestSamples.*;
import static tech.luau.appsalonmng.domain.StockHistoryTestSamples.*;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class StockHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockHistory.class);
        StockHistory stockHistory1 = getStockHistorySample1();
        StockHistory stockHistory2 = new StockHistory();
        assertThat(stockHistory1).isNotEqualTo(stockHistory2);

        stockHistory2.setId(stockHistory1.getId());
        assertThat(stockHistory1).isEqualTo(stockHistory2);

        stockHistory2 = getStockHistorySample2();
        assertThat(stockHistory1).isNotEqualTo(stockHistory2);
    }

    @Test
    void productTest() throws Exception {
        StockHistory stockHistory = getStockHistoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        stockHistory.setProduct(productBack);
        assertThat(stockHistory.getProduct()).isEqualTo(productBack);

        stockHistory.product(null);
        assertThat(stockHistory.getProduct()).isNull();
    }
}
