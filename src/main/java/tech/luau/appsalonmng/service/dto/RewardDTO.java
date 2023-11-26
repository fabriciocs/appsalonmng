package tech.luau.appsalonmng.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tech.luau.appsalonmng.domain.Reward} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RewardDTO implements Serializable {

    private Long id;

    @Min(value = 0)
    private Integer points;

    @Lob
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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
        if (!(o instanceof RewardDTO)) {
            return false;
        }

        RewardDTO rewardDTO = (RewardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rewardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RewardDTO{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
