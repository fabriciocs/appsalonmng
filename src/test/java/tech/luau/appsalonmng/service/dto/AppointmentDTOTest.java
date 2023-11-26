package tech.luau.appsalonmng.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class AppointmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppointmentDTO.class);
        AppointmentDTO appointmentDTO1 = new AppointmentDTO();
        appointmentDTO1.setId(1L);
        AppointmentDTO appointmentDTO2 = new AppointmentDTO();
        assertThat(appointmentDTO1).isNotEqualTo(appointmentDTO2);
        appointmentDTO2.setId(appointmentDTO1.getId());
        assertThat(appointmentDTO1).isEqualTo(appointmentDTO2);
        appointmentDTO2.setId(2L);
        assertThat(appointmentDTO1).isNotEqualTo(appointmentDTO2);
        appointmentDTO1.setId(null);
        assertThat(appointmentDTO1).isNotEqualTo(appointmentDTO2);
    }
}
