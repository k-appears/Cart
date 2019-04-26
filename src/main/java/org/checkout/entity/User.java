package org.checkout.entity;

public class User {
    private final String name;
    private final String billingAddress;
    private final Basket basket;

    public User(String name, String billingAddress, Basket basket) {
        this.name = name;
        this.billingAddress = billingAddress;
        this.basket = basket;
    }

    public String getName() {
        return this.name;
    }
}
