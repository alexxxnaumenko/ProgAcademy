package enums;

public enum TransactionType {

    WITHDRAWAL("%s %s was withdrawn"),
    DEPOSIT("%s %s was deposited"),
    RECEIVING_AFTER_TRANSFER("%s %s was transferred from user id %s"),
    TRANSFER("Transfer %s %s to the user id: %s "),
    CONVERT("Convert from %s %s to %s %s ");

    private final String message;

    TransactionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
