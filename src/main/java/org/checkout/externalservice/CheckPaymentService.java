package org.checkout.externalservice;

import org.checkout.datatransferobject.ResponsePayment;

public abstract class CheckPaymentService implements Service {
    @Override
    public abstract ResponsePayment perform();
}
