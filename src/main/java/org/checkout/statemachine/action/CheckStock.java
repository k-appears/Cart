package org.checkout.statemachine.action;

import org.checkout.datatransferobject.ResponseStock;
import org.checkout.externalservice.CheckStockService;
import org.checkout.mapper.Mapper;
import org.checkout.statemachine.state.CartReady;
import org.checkout.statemachine.state.State;
import org.checkout.statemachine.state.stock.CheckedStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckStock extends Action {

    private static final Logger logger = LoggerFactory.getLogger(CheckStock.class);
    private final CheckStockService service;

    public CheckStock(CheckStockService service) {
        this.service = service;
    }

    @Override
    public <S extends State> CheckedStock from(S state) {
        if (!(state instanceof CartReady)) {
            throw new IllegalArgumentException(state + " is not instance of " + CartReady.class.getName());
        }
        ResponseStock responseStock = service.perform();
        CheckedStock checkedStock = Mapper.map(responseStock, state.getUser());
        logger.info(String.format("User '%s' in action '%s' from state '%s' to %s", state.getUser().getName(), this.getClass().getSimpleName(), state.getClass().getSimpleName(), checkedStock.getClass().getSimpleName()));
        return checkedStock;
    }
}