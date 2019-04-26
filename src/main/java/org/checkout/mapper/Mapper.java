package org.checkout.mapper;

import org.checkout.datatransferobject.ResponsePayment;
import org.checkout.datatransferobject.ResponseStock;
import org.checkout.entity.User;
import org.checkout.statemachine.state.payment.CheckedPayment;
import org.checkout.statemachine.state.payment.PaymentFailure;
import org.checkout.statemachine.state.payment.PaymentSuccessful;
import org.checkout.statemachine.state.stock.CheckedStock;
import org.checkout.statemachine.state.stock.CorrectStock;
import org.checkout.statemachine.state.stock.OutOfStock;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class can be replaced by a Factory if complexity increases @see <a href="https://en.wikipedia.org/wiki/Factory_method_pattern">Factory pattern</a>
 */
public class Mapper {
    public static CheckedStock map(ResponseStock responseStock, User user) {
        switch (responseStock) {
            case STOCK_CORRECT:
                return new CorrectStock(user);
            case OUT_OF_STOCK:
                return new OutOfStock(user);
            default:
                throw new IllegalArgumentException(responseStock + " not in: " +
                        Stream.of(ResponseStock.values()).map(Enum::name).collect(Collectors.joining(",")));
        }
    }


    public static CheckedPayment map(ResponsePayment responsePayment, User user) {
        switch (responsePayment.getValue()) {
            case SUCCESS:
                return new PaymentSuccessful(user);
            case FAILURE:
                return new PaymentFailure(user, responsePayment.getMessage());
            default:
                throw new IllegalArgumentException(responsePayment + " not in: " +
                        Stream.of(ResponsePayment.Value.values()).map(Enum::name).collect(Collectors.joining(",")));
        }
    }
}
