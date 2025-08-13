package ag.selm.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public record NewProductReviewDto(

        @NotNull(message = "{feedback.products.reviews.create.errors.id_is_null}")
        Integer productId,

        @NotNull(message = "{feedback.products.reviews.create.errors.rating_is_null}")
        @Min(value = 1, message = "{feedback.products.reviews.create.errors.rating_is_below_min}")
        @Max(value = 5, message = "{feedback.products.reviews.create.errors.rating_is_abow_max}")
        Integer rating,

        @NotNull(message = "{feedback.products.reviews.create.errors.review_is_null}")
        @Size(min = 5, max = 255, message = "{feedback.products.reviews.create.errors.review_is_too_big}")
        String review
) {
}
