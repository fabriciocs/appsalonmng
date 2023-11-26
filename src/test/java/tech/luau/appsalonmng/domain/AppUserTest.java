package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.AppUserTestSamples.*;
import static tech.luau.appsalonmng.domain.ClientTestSamples.*;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void clientTest() throws Exception {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        appUser.setClient(clientBack);
        assertThat(appUser.getClient()).isEqualTo(clientBack);

        appUser.client(null);
        assertThat(appUser.getClient()).isNull();
    }
}
