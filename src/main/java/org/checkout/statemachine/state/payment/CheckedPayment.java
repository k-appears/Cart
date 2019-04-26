package org.checkout.statemachine.state.payment;

import org.checkout.entity.User;
import org.checkout.statemachine.state.State;

public abstract class CheckedPayment extends State {

    public CheckedPayment(User user) {
        super(user);
    }
}
