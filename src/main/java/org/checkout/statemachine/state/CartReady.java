package org.checkout.statemachine.state;

import org.checkout.entity.User;

public class CartReady extends State {

    public CartReady(User user) {
        super(user);
    }
}
