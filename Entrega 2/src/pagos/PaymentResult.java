package pagos;

public class PaymentResult {
    private boolean success;
    private String message;

    // Constructor
    public PaymentResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}


