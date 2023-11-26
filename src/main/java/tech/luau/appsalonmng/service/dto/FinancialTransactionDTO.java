package tech.luau.appsalonmng.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link tech.luau.appsalonmng.domain.FinancialTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FinancialTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionType;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal amount;

    @NotNull
    private ZonedDateTime dateTime;

    @Lob
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinancialTransactionDTO)) {
            return false;
        }

        FinancialTransactionDTO financialTransactionDTO = (FinancialTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, financialTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinancialTransactionDTO{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", amount=" + getAmount() +
            ", dateTime='" + getDateTime() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
