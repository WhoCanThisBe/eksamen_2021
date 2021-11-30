package com.pgr301.exam;

import com.pgr301.exam.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReallyShakyBankingCoreSystemServiceTest {


    @Test
    void stupidTest() {
        String stringTest =  "test1211111";
        assertEquals(stringTest,"test1211111");
    }

    @Test
    void testAccount(){
        Account account = new Account();
        Assertions.assertEquals("NOK", account.getCurrency());
    }

}