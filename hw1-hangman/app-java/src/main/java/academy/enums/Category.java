package academy.enums;

public enum Category {
    ANIMALS("Animal"),
    FOOD("Food"),
    CLOTHES("Clothes"),
    TECHNOLOGIES("Technologies");
    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
