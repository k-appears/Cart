package org.checkout.statemachine.state.stock;

import org.checkout.entity.User;

public class OutOfStock extends CheckedStock {
    public OutOfStock(User user) {
        super(user);
    }
}
