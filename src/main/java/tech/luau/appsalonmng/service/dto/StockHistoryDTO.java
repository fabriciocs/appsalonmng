package tech.luau.appsalonmng.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link tech.luau.appsalonmng.domain.StockHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantityChanged;

    @NotNull
    private ZonedDateTime dateTime;

    private String reason;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(Integer quantityChanged) {
        this.quantityChanged = quantityChanged;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockHistoryDTO)) {
            return false;
        }

        StockHistoryDTO stockHistoryDTO = (StockHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stockHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockHistoryDTO{" +
            "id=" + getId() +
            ", quantityChanged=" + getQuantityChanged() +
            ", dateTime='" + getDateTime() + "'" +
            ", reason='" + getReason() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}
