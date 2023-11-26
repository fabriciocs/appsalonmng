package tech.luau.appsalonmng.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AppointmentMapperTest {

    private AppointmentMapper appointmentMapper;

    @BeforeEach
    public void setUp() {
        appointmentMapper = new AppointmentMapperImpl();
    }
}
