package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.AppServiceTestSamples.*;
import static tech.luau.appsalonmng.domain.ProductTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class AppServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppService.class);
        AppService appService1 = getAppServiceSample1();
        AppService appService2 = new AppService();
        assertThat(appService1).isNotEqualTo(appService2);

        appService2.setId(appService1.getId());
        assertThat(appService1).isEqualTo(appService2);

        appService2 = getAppServiceSample2();
        assertThat(appService1).isNotEqualTo(appService2);
    }

    @Test
    void productsTest() throws Exception {
        AppService appService = getAppServiceRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        appService.addProducts(productBack);
        assertThat(appService.getProducts()).containsOnly(productBack);

        appService.removeProducts(productBack);
        assertThat(appService.getProducts()).doesNotContain(productBack);

        appService.products(new HashSet<>(Set.of(productBack)));
        assertThat(appService.getProducts()).containsOnly(productBack);

        appService.setProducts(new HashSet<>());
        assertThat(appService.getProducts()).doesNotContain(productBack);
    }
}
