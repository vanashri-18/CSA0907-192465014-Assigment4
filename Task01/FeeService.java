public class FeeService {

    public boolean validate(String regNo, String amount) {
        if (regNo == null || regNo.trim().isEmpty()) {
            return false;
        }

        try {
            double value = Double.parseDouble(amount);

            if (value <= 0) {
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String payFee(String regNo, String amount) {
        if (!validate(regNo, amount)) {
            return "Invalid registration number or amount!";
        }

        return "Payment Successful!";
    }

    public String generateReceipt(String regNo, String amount) {
        return "FEE RECEIPT\nRegister Number: " + regNo
                + "\nAmount: Rs. " + amount;
    }
}