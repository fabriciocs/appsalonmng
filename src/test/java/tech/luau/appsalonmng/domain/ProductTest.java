package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.AppServiceTestSamples.*;
import static tech.luau.appsalonmng.domain.ProductTestSamples.*;
import static tech.luau.appsalonmng.domain.StockHistoryTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void stockHistoriesTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        StockHistory stockHistoryBack = getStockHistoryRandomSampleGenerator();

        product.addStockHistories(stockHistoryBack);
        assertThat(product.getStockHistories()).containsOnly(stockHistoryBack);
        assertThat(stockHistoryBack.getProduct()).isEqualTo(product);

        product.removeStockHistories(stockHistoryBack);
        assertThat(product.getStockHistories()).doesNotContain(stockHistoryBack);
        assertThat(stockHistoryBack.getProduct()).isNull();

        product.stockHistories(new HashSet<>(Set.of(stockHistoryBack)));
        assertThat(product.getStockHistories()).containsOnly(stockHistoryBack);
        assertThat(stockHistoryBack.getProduct()).isEqualTo(product);

        product.setStockHistories(new HashSet<>());
        assertThat(product.getStockHistories()).doesNotContain(stockHistoryBack);
        assertThat(stockHistoryBack.getProduct()).isNull();
    }

    @Test
    void appServicesTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        AppService appServiceBack = getAppServiceRandomSampleGenerator();

        product.addAppServices(appServiceBack);
        assertThat(product.getAppServices()).containsOnly(appServiceBack);
        assertThat(appServiceBack.getProducts()).containsOnly(product);

        product.removeAppServices(appServiceBack);
        assertThat(product.getAppServices()).doesNotContain(appServiceBack);
        assertThat(appServiceBack.getProducts()).doesNotContain(product);

        product.appServices(new HashSet<>(Set.of(appServiceBack)));
        assertThat(product.getAppServices()).containsOnly(appServiceBack);
        assertThat(appServiceBack.getProducts()).containsOnly(product);

        product.setAppServices(new HashSet<>());
        assertThat(product.getAppServices()).doesNotContain(appServiceBack);
        assertThat(appServiceBack.getProducts()).doesNotContain(product);
    }
}
