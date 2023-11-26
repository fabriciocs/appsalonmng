package tech.luau.appsalonmng.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Min(value = 0)
    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<StockHistory> stockHistories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Set<AppService> appServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantityInStock() {
        return this.quantityInStock;
    }

    public Product quantityInStock(Integer quantityInStock) {
        this.setQuantityInStock(quantityInStock);
        return this;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Product price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<StockHistory> getStockHistories() {
        return this.stockHistories;
    }

    public void setStockHistories(Set<StockHistory> stockHistories) {
        if (this.stockHistories != null) {
            this.stockHistories.forEach(i -> i.setProduct(null));
        }
        if (stockHistories != null) {
            stockHistories.forEach(i -> i.setProduct(this));
        }
        this.stockHistories = stockHistories;
    }

    public Product stockHistories(Set<StockHistory> stockHistories) {
        this.setStockHistories(stockHistories);
        return this;
    }

    public Product addStockHistories(StockHistory stockHistory) {
        this.stockHistories.add(stockHistory);
        stockHistory.setProduct(this);
        return this;
    }

    public Product removeStockHistories(StockHistory stockHistory) {
        this.stockHistories.remove(stockHistory);
        stockHistory.setProduct(null);
        return this;
    }

    public Set<AppService> getAppServices() {
        return this.appServices;
    }

    public void setAppServices(Set<AppService> appServices) {
        if (this.appServices != null) {
            this.appServices.forEach(i -> i.removeProducts(this));
        }
        if (appServices != null) {
            appServices.forEach(i -> i.addProducts(this));
        }
        this.appServices = appServices;
    }

    public Product appServices(Set<AppService> appServices) {
        this.setAppServices(appServices);
        return this;
    }

    public Product addAppServices(AppService appService) {
        this.appServices.add(appService);
        appService.getProducts().add(this);
        return this;
    }

    public Product removeAppServices(AppService appService) {
        this.appServices.remove(appService);
        appService.getProducts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantityInStock=" + getQuantityInStock() +
            ", price=" + getPrice() +
            "}";
    }
}
