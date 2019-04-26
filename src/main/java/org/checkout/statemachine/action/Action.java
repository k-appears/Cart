package org.checkout.statemachine.action;

import org.checkout.statemachine.state.State;

abstract class Action {
    abstract <S extends State> State from(S state);
}
