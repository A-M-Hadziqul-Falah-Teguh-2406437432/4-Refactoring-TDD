package id.ac.ui.cs.advprog.eshop.service;

public class BankTransferValidator {
    public boolean isValid(String bankName, String referenceCode) {
        return isNotBlank(bankName) && isNotBlank(referenceCode);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
