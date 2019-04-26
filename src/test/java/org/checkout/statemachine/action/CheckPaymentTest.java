package org.checkout.statemachine.action;

import org.checkout.datatransferobject.ResponsePayment;
import org.checkout.entity.Basket;
import org.checkout.entity.Item;
import org.checkout.entity.User;
import org.checkout.externalservice.CheckPaymentService;
import org.checkout.statemachine.state.CartReady;
import org.checkout.statemachine.state.State;
import org.checkout.statemachine.state.payment.PaymentFailure;
import org.checkout.statemachine.state.payment.PaymentSuccessful;
import org.checkout.statemachine.state.stock.CorrectStock;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.checkout.datatransferobject.ResponsePayment.Value.FAILURE;
import static org.checkout.datatransferobject.ResponsePayment.Value.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckPaymentTest {

    private CheckPayment checkPayment;
    private CheckPaymentService successService = new CheckPaymentService() {
        @Override
        public ResponsePayment perform() {
            return new ResponsePayment(SUCCESS, "");
        }
    };
    private CheckPaymentService failureService = new CheckPaymentService() {
        @Override
        public ResponsePayment perform() {
            return new ResponsePayment(FAILURE, "Any Error");
        }
    };

    private Item item1 = new Item(Item.Size.S, "Red", BigDecimal.valueOf(1.11));
    private Item item2 = new Item(Item.Size.M, "Red", BigDecimal.valueOf(2.22));
    private Item item3 = new Item(Item.Size.L, "Red", BigDecimal.valueOf(3.33));
    private User user1 = new User("UserName1", "Address1", new Basket(Stream.of(item1, item2, item3).collect(Collectors.toSet())));


    @Test
    public void from_IncorrectInitialState_throwsIllegalException() {
        State cartReady = new CartReady(user1);
        checkPayment = new CheckPayment(successService);

        assertThatThrownBy(() -> checkPayment.from(cartReady))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CartReady");
    }

    @Test
    public void from_Success() {
        checkPayment = new CheckPayment(successService);
        State correctStock = new CorrectStock(user1);
        State result = checkPayment.from(correctStock);

        assertTrue(result instanceof PaymentSuccessful);
        assertEquals(result.getUser(), user1);
    }

    @Test
    public void from_Failure() {
        checkPayment = new CheckPayment(failureService);
        State correctStock = new CorrectStock(user1);
        State result = checkPayment.from(correctStock);

        assertTrue(result instanceof PaymentFailure);
        assertEquals(result.getUser(), user1);
    }
}