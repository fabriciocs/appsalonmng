package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.AppUserTestSamples.*;
import static tech.luau.appsalonmng.domain.ClientTestSamples.*;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void appUserTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        client.setAppUser(appUserBack);
        assertThat(client.getAppUser()).isEqualTo(appUserBack);
        assertThat(appUserBack.getClient()).isEqualTo(client);

        client.appUser(null);
        assertThat(client.getAppUser()).isNull();
        assertThat(appUserBack.getClient()).isNull();
    }
}
