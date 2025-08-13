package ag.selm.feedback.dto;

import jakarta.validation.constraints.NotNull;

public record NewFavoriteProductDto(
        @NotNull(message = "{feedback.products.favorites.create.errors.id_is_null}")
        int productId) {
}
