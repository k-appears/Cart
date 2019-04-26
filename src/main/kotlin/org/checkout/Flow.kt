package org.checkout

import mu.KotlinLogging
import java.math.BigDecimal


val logger = KotlinLogging.logger {}

sealed class Action {
    class CheckStock : (State.CartReady, (User) -> State.StockChecked) -> State.StockChecked {
        override operator fun invoke(
            state: State.CartReady,
            stockChecker: (User) -> State.StockChecked
        ): State.StockChecked {
            logger.info { "user '${state.user.name}' from state '${state.javaClass.simpleName}' passed from action '${this.javaClass.simpleName}'" }
            return stockChecker(state.user)
        }

    }

    class CheckPayment : (State.StockChecked, (User) -> State.Payment) -> State.Payment {
        override operator fun invoke(
            state: State.StockChecked,
            paymentGateway: (User) -> State.Payment
        ): State.Payment {
            when (state) {
                is State.StockChecked.Correct -> {
                    logger.info { "user '${state.user.name}' from state ${state.javaClass.simpleName} passed from action ${this.javaClass.simpleName}" }
                    return paymentGateway(state.user)
                }
                else -> throw IllegalStateException("Invalid state $state passed from action $this")
            }
        }
    }
}


sealed class State {
    data class CartReady(val user: User) : State()

    sealed class StockChecked : State() {
        data class Correct(val user: User) : StockChecked()
        data class OutOfStock(val user: User, val items: List<Item>) : StockChecked()
    }

    sealed class Payment : State() {
        data class Successful(val user: User) : Payment()
        data class Failure(val user: User, val message: String) : Payment()
    }
}


data class User(
    val name: String,
    val billingAddress: String,
    val basket: Basket
)

data class Basket(val items: Set<Item>)

data class Item(
    val size: Size,
    val color: String,
    val price: BigDecimal? = BigDecimal.ZERO // Better from use NumberFormat if you want from change currency -> NumberFormat.getCurrencyInstance(java.util.Locale.GERMANY);
)

enum class Size {
    XS, S, M, L, XL
}