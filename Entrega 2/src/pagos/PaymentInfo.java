package pagos;

public class PaymentInfo {
    private double amount;
    private String currency;
    private String transactionId;

    // Constructor
    public PaymentInfo(double amount, String currency, String transactionId) {
        this.amount = amount;
        this.currency = currency;
        this.transactionId = transactionId;
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionId() {
        return transactionId;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}


