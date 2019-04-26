package org.checkout.externalservice;

import org.checkout.datatransferobject.ResponseStock;

public abstract class CheckStockService implements Service {
    @Override
    public abstract ResponseStock perform();
}
