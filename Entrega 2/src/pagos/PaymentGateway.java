package pagos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface PaymentGateway {
    PaymentResult processPayment(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo);

    default boolean isValidCreditCard(CreditCardInfo creditCardInfo) {
        return validateCardNumber(creditCardInfo.getCardNumber()) &&
               validateExpirationDate(creditCardInfo.getExpirationDate()) &&
               validateCVV(creditCardInfo.getCvv());
    }

    default boolean hasSufficientFunds(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo) {
        double accountBalance = getAccountBalance(creditCardInfo);
        return paymentInfo.getAmount() <= accountBalance;
    }

    // Métodos auxiliares de validación
    default boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}"); // Validar que tenga 16 dígitos
    }

    default boolean validateExpirationDate(String expirationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        dateFormat.setLenient(false);

        try {
            Date expDate = dateFormat.parse(expirationDate);
            Date currentDate = new Date();
            return expDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    default boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}"); // Validar que tenga 3 dígitos
    }

    default double getAccountBalance(CreditCardInfo creditCardInfo) {
        return 10000.0; // Balance ficticio
    }
}


