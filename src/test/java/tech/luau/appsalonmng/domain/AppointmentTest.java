package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.AppServiceTestSamples.*;
import static tech.luau.appsalonmng.domain.AppUserTestSamples.*;
import static tech.luau.appsalonmng.domain.AppointmentTestSamples.*;
import static tech.luau.appsalonmng.domain.ClientTestSamples.*;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class AppointmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointment.class);
        Appointment appointment1 = getAppointmentSample1();
        Appointment appointment2 = new Appointment();
        assertThat(appointment1).isNotEqualTo(appointment2);

        appointment2.setId(appointment1.getId());
        assertThat(appointment1).isEqualTo(appointment2);

        appointment2 = getAppointmentSample2();
        assertThat(appointment1).isNotEqualTo(appointment2);
    }

    @Test
    void clientTest() throws Exception {
        Appointment appointment = getAppointmentRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        appointment.setClient(clientBack);
        assertThat(appointment.getClient()).isEqualTo(clientBack);

        appointment.client(null);
        assertThat(appointment.getClient()).isNull();
    }

    @Test
    void appServiceTest() throws Exception {
        Appointment appointment = getAppointmentRandomSampleGenerator();
        AppService appServiceBack = getAppServiceRandomSampleGenerator();

        appointment.setAppService(appServiceBack);
        assertThat(appointment.getAppService()).isEqualTo(appServiceBack);

        appointment.appService(null);
        assertThat(appointment.getAppService()).isNull();
    }

    @Test
    void appUserTest() throws Exception {
        Appointment appointment = getAppointmentRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        appointment.setAppUser(appUserBack);
        assertThat(appointment.getAppUser()).isEqualTo(appUserBack);

        appointment.appUser(null);
        assertThat(appointment.getAppUser()).isNull();
    }
}
