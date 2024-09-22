package ru.otus.bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void initTest() {
        Main.init();
        Assertions.assertNotNull(Main.agreementService);
        Assertions.assertNotNull(Main.accountService);
        Assertions.assertNotNull(Main.paymentProcessor);
    }

}
