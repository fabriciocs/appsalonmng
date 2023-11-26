package tech.luau.appsalonmng.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class FinancialTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialTransactionDTO.class);
        FinancialTransactionDTO financialTransactionDTO1 = new FinancialTransactionDTO();
        financialTransactionDTO1.setId(1L);
        FinancialTransactionDTO financialTransactionDTO2 = new FinancialTransactionDTO();
        assertThat(financialTransactionDTO1).isNotEqualTo(financialTransactionDTO2);
        financialTransactionDTO2.setId(financialTransactionDTO1.getId());
        assertThat(financialTransactionDTO1).isEqualTo(financialTransactionDTO2);
        financialTransactionDTO2.setId(2L);
        assertThat(financialTransactionDTO1).isNotEqualTo(financialTransactionDTO2);
        financialTransactionDTO1.setId(null);
        assertThat(financialTransactionDTO1).isNotEqualTo(financialTransactionDTO2);
    }
}
