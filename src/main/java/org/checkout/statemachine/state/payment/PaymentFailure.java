package org.checkout.statemachine.state.payment;

import org.checkout.entity.User;

public class PaymentFailure extends CheckedPayment {
    private final String message;

    public PaymentFailure(User user, String message) {
        super(user);
        this.message = message;
    }
}
