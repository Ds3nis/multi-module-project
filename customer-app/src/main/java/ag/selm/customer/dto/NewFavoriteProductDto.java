package ag.selm.customer.dto;

import java.util.UUID;

public record NewFavoriteProductDto(

        UUID id,

        int productId) {
}
