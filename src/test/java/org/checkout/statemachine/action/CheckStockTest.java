package org.checkout.statemachine.action;

import org.checkout.datatransferobject.ResponseStock;
import org.checkout.entity.Basket;
import org.checkout.entity.Item;
import org.checkout.entity.User;
import org.checkout.externalservice.CheckStockService;
import org.checkout.statemachine.state.CartReady;
import org.checkout.statemachine.state.State;
import org.checkout.statemachine.state.stock.CorrectStock;
import org.checkout.statemachine.state.stock.OutOfStock;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.checkout.datatransferobject.ResponseStock.OUT_OF_STOCK;
import static org.checkout.datatransferobject.ResponseStock.STOCK_CORRECT;
import static org.junit.Assert.*;

public class CheckStockTest {

    private CheckStock checkStock;
    private CheckStockService successService = new CheckStockService() {
        @Override
        public ResponseStock perform() {
            return STOCK_CORRECT;
        }
    };
    private CheckStockService failureService = new CheckStockService() {
        @Override
        public ResponseStock perform() {
            return OUT_OF_STOCK;
        }
    };

    private Item item1 = new Item(Item.Size.S, "Red", BigDecimal.valueOf(1.11));
    private Item item2 = new Item(Item.Size.M, "Red", BigDecimal.valueOf(2.22));
    private Item item3 = new Item(Item.Size.L, "Red", BigDecimal.valueOf(3.33));
    private User user1 = new User("UserName1", "Address1", new Basket(Stream.of(item1, item2, item3).collect(Collectors.toSet())));


    @Test
    public void from_IncorrectInitialState_throwsIllegalException() {
        State cartReady = new CorrectStock(user1);
        checkStock = new CheckStock(successService);

        assertThatThrownBy(() -> checkStock.from(cartReady))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CartReady");
    }

    @Test
    public void from_StockCorrect() {
        checkStock = new CheckStock(successService);
        State cartReady = new CartReady(user1);
        State result = checkStock.from(cartReady);

        assertNotNull(result);
        assertTrue(result instanceof CorrectStock);
        assertEquals(result.getUser(), user1);
    }

    @Test
    public void from_OutOfStock() {
        checkStock = new CheckStock(failureService);
        State cartReady = new CartReady(user1);
        State result = checkStock.from(cartReady);

        assertNotNull(result);
        assertTrue(result instanceof OutOfStock);
        assertEquals(result.getUser(), user1);
    }
}