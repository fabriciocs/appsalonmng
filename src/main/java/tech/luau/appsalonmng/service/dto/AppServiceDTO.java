package tech.luau.appsalonmng.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link tech.luau.appsalonmng.domain.AppService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private String description;

    @NotNull
    private Integer duration;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal price;

    private Set<ProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppServiceDTO)) {
            return false;
        }

        AppServiceDTO appServiceDTO = (AppServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            ", products=" + getProducts() +
            "}";
    }
}
