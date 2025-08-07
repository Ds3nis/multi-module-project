package ag.selm.customer.extension;

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
