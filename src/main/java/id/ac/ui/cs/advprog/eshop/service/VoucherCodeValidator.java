package id.ac.ui.cs.advprog.eshop.service;

public class VoucherCodeValidator {
    private static final int VOUCHER_LENGTH = 16;
    private static final int VOUCHER_NUMERIC_COUNT = 8;
    private static final String VOUCHER_PREFIX = "ESHOP";

    public boolean isValid(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != VOUCHER_LENGTH || !voucherCode.startsWith(VOUCHER_PREFIX)) {
            return false;
        }
        return voucherCode.chars().filter(Character::isDigit).count() == VOUCHER_NUMERIC_COUNT;
    }
}
