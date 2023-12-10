package pagos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayPalGateway implements PaymentGateway {

	String logEntry;

    @Override
    public PaymentResult processPayment(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo) {
        boolean paymentSuccess = simulatePaymentProcessing(creditCardInfo, paymentInfo);
        String message = paymentSuccess ? "Pago procesado exitosamente por PayPal" : "Error en el procesamiento del pago por PayU";

        // Registro de la transacción
        logEntry = logTransaction(creditCardInfo, paymentInfo, paymentSuccess);

        return new PaymentResult(paymentSuccess, message);
    }

    private boolean simulatePaymentProcessing(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo) {
        // Utiliza los métodos por defecto de la interfaz para la validación
        if (!isValidCreditCard(creditCardInfo) || !hasSufficientFunds(creditCardInfo, paymentInfo)) {
            return false;
        }
        return authorizePayment(creditCardInfo, paymentInfo);
    }

    private boolean authorizePayment(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo) {
        // Aquí puedes agregar lógica adicional para autorizar el pago
        return true; // Simulación de autorización exitosa
    }

    private String logTransaction(CreditCardInfo creditCardInfo, PaymentInfo paymentInfo, boolean success) {
        
        String logEntry = createLogEntry(paymentInfo, success);
        return logEntry;
        
    }

    public String getLogEntry() {
        return logEntry;
    }

    private String createLogEntry(PaymentInfo paymentInfo, boolean success) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return timestamp + " - Transacción: " + paymentInfo.getTransactionId() +
               ", Monto: " + paymentInfo.getAmount() + ", Moneda: " + paymentInfo.getCurrency() +
               ", Éxito: " + success + "\n";
    }
}






