package ag.selm.customer.dto;


public record NewProductReviewDto(

        Integer productId,

        Integer rating,

        String review
) {
}
