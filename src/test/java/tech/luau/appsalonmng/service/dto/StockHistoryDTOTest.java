package tech.luau.appsalonmng.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.luau.appsalonmng.web.rest.TestUtil;

class StockHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockHistoryDTO.class);
        StockHistoryDTO stockHistoryDTO1 = new StockHistoryDTO();
        stockHistoryDTO1.setId(1L);
        StockHistoryDTO stockHistoryDTO2 = new StockHistoryDTO();
        assertThat(stockHistoryDTO1).isNotEqualTo(stockHistoryDTO2);
        stockHistoryDTO2.setId(stockHistoryDTO1.getId());
        assertThat(stockHistoryDTO1).isEqualTo(stockHistoryDTO2);
        stockHistoryDTO2.setId(2L);
        assertThat(stockHistoryDTO1).isNotEqualTo(stockHistoryDTO2);
        stockHistoryDTO1.setId(null);
        assertThat(stockHistoryDTO1).isNotEqualTo(stockHistoryDTO2);
    }
}
