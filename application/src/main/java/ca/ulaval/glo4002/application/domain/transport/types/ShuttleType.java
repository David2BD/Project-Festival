package ca.ulaval.glo4002.application.domain.transport.types;

import ca.ulaval.glo4002.application.domain.MoneyAmount;

public enum ShuttleType {
    ET_SPACESHIP("ET Spaceship", new MoneyAmount(100000)),
    MILLENNIUM_FALCON("Millenium Falcon", new MoneyAmount(65000)),
    SPACE_X("SpaceX", new MoneyAmount(30000));

    private final String name;
    private final MoneyAmount price;

    ShuttleType(String name, MoneyAmount price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public MoneyAmount getPrice() {
        return price;
    }
}

