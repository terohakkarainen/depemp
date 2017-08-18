package fi.thakki.depemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import fi.thakki.depemp.dao.GenericDao;

public class TransactionSupportingTestBase {

    @Autowired
    private GenericDao myGenericDao;

    protected GenericDao genericDao() {
        return myGenericDao;
    }

    protected void assertions(
            Runnable assertions) {
        executeTransactionally(assertions);
    }

    @Transactional(readOnly = true)
    private void executeTransactionally(
            Runnable runnable) {
        runnable.run();
    }
}
