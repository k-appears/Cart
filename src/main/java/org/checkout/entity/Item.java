package org.checkout.entity;

import java.math.BigDecimal;

public class Item {

    private final Size size;
    private final String color;
    private final BigDecimal price;

    public Item(Size size, String color, BigDecimal price) {
        this.size = size;
        this.color = color;
        this.price = price != null && price.doubleValue() > 0 ? price : BigDecimal.ZERO;
    }

    public enum Size {
        XS, S, M, L, XL
    }
}
