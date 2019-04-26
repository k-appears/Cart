package org.checkout.statemachine.state.stock;

import org.checkout.entity.User;

public class CorrectStock extends CheckedStock {
    public CorrectStock(User user) {
        super(user);
    }
}
