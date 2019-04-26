package org.checkout.entity;

import java.util.Set;

public class Basket {
    private final Set<Item> items;

    public Basket(Set<Item> items) {
        this.items = items;
    }
}
