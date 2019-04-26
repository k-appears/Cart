package org.checkout

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertFailsWith

class FlowTest {

    private var item1 = Item(Size.S, "Red", BigDecimal.valueOf(1.11))
    private var item2 = Item(Size.M, "Red", BigDecimal.valueOf(2.22))
    private var item3 = Item(Size.L, "Red", BigDecimal.valueOf(3.33))
    private var user1 = User(name = "User1", billingAddress = "Address1", basket = Basket(setOf(item1, item2, item3)))
    private var cartReady = State.CartReady(user1)

    private val checkedStockCorrect = { user: User -> State.StockChecked.Correct(user) }
    private val checkedStockOutOfStock = { user: User -> State.StockChecked.OutOfStock(user, listOf(item3)) }
    private val paymentSuccess = { user: User -> State.Payment.Successful(user) }
    private val paymentFailure = { user: User -> State.Payment.Failure(user, "error") }

    @Test
    fun checkStock_IsOutOfStock() {
        val checkStock = Action.CheckStock()(cartReady, checkedStockOutOfStock)

        checkStock as State.StockChecked.OutOfStock
        assertThat(checkStock.user, `is`(cartReady.user))
    }

    @Test
    fun outOfStock_throwsException_WhenCastedToCorrect() {
        val checkStock = Action.CheckStock()(cartReady, checkedStockOutOfStock)

        assertFailsWith(ClassCastException::class) { checkStock as State.StockChecked.Correct }
    }

    @Test
    fun stockCorrectPaymentFailure() {
        val checkStock = Action.CheckStock()(cartReady, checkedStockCorrect)
        val payment = Action.CheckPayment()(checkStock, paymentFailure)

        checkStock as State.StockChecked.Correct
        assertThat(checkStock.user, `is`(cartReady.user))
        payment as State.Payment.Failure
        assertThat(payment.user, `is`(cartReady.user))
    }

    @Test
    fun checkPayment_throwsException_WhenInputOutOfStock() {
        val outOfStock = State.StockChecked.OutOfStock(user1, listOf(item1))
        assertFailsWith(IllegalStateException::class) { Action.CheckPayment()(outOfStock, paymentSuccess) }
    }

    @Test
    fun stockCorrectPaymentSuccess() {
        val checkStock = Action.CheckStock()(cartReady, checkedStockCorrect)
        val payment = Action.CheckPayment()(checkStock, paymentSuccess)

        checkStock as State.StockChecked.Correct
        assertThat(checkStock.user, `is`(cartReady.user))
        payment as State.Payment.Successful
        assertThat(payment.user, `is`(cartReady.user))
    }
}