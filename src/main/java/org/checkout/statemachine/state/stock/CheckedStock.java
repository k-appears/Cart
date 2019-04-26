package org.checkout.statemachine.state.stock;

import org.checkout.entity.User;
import org.checkout.statemachine.state.State;

public abstract class CheckedStock extends State {

    public CheckedStock(User user) {
        super(user);
    }
}
