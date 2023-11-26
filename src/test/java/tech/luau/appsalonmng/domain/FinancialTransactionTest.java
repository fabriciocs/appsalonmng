package tech.luau.appsalonmng.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.luau.appsalonmng.domain.FinancialTransactionTestSamples.*;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class FinancialTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialTransaction.class);
        FinancialTransaction financialTransaction1 = getFinancialTransactionSample1();
        FinancialTransaction financialTransaction2 = new FinancialTransaction();
        assertThat(financialTransaction1).isNotEqualTo(financialTransaction2);

        financialTransaction2.setId(financialTransaction1.getId());
        assertThat(financialTransaction1).isEqualTo(financialTransaction2);

        financialTransaction2 = getFinancialTransactionSample2();
        assertThat(financialTransaction1).isNotEqualTo(financialTransaction2);
    }
}
