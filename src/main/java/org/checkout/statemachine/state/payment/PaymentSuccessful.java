package org.checkout.statemachine.state.payment;

import org.checkout.entity.User;

public class PaymentSuccessful extends CheckedPayment {
    public PaymentSuccessful(User user) {
        super(user);
    }
}
