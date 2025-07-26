package ag.selm.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductDto(

        @NotNull(message = "{catalog.products.update.error.title_is_null}")
        @Size(min = 3, max = 50, message = "{catalog.products.update.errors.title_size_is_invalid}")
        String title,

        @NotNull(message = "{catalog.products.update.errors.details_is_null}")
        @Size(max = 1000, message = "{catalog.products.update.errors.details_size_is_invalid}")
        String details
) {
}
