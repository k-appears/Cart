package org.checkout.statemachine.state;

import org.checkout.entity.User;

public abstract class State {

    private User user;

    public State(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
