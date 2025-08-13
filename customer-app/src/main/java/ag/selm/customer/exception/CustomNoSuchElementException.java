package ag.selm.customer.exception;

public class CustomNoSuchElementException extends NoSuchFieldException{

    private Integer productId;

    public CustomNoSuchElementException(String message, Integer productId) {
        super(message);
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
