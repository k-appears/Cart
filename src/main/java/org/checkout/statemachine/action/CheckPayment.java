package org.checkout.statemachine.action;

import org.checkout.datatransferobject.ResponsePayment;
import org.checkout.externalservice.CheckPaymentService;
import org.checkout.mapper.Mapper;
import org.checkout.statemachine.state.State;
import org.checkout.statemachine.state.payment.CheckedPayment;
import org.checkout.statemachine.state.stock.CheckedStock;
import org.checkout.statemachine.state.stock.CorrectStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckPayment extends Action {

    private static final Logger logger = LoggerFactory.getLogger(CheckPayment.class);
    private final CheckPaymentService service;

    public CheckPayment(CheckPaymentService service) {
        this.service = service;
    }

    @Override
    public <S extends State> CheckedPayment from(S state) {
        if (!(state instanceof CorrectStock)) {
            throw new IllegalArgumentException(state + " is not instance of " + CheckedStock.class.getName());
        }

        ResponsePayment responsePayment = service.perform();
        CheckedPayment checkPayment = Mapper.map(responsePayment, state.getUser());
        logger.info(String.format("User '%s' in action '%s' from state '%s' to %s", state.getUser().getName(), this.getClass().getSimpleName(), state.getClass().getSimpleName(), checkPayment.getClass().getSimpleName()));
        return checkPayment;
    }
}
