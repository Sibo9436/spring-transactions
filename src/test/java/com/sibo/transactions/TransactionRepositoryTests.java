package com.sibo.transactions;

import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.model.Currency;
import com.sibo.transactions.repository.transaction.TransactionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("jdbctest")
@ComponentScan("com.sibo")
public class TransactionRepositoryTests {

    @Autowired
    TransactionDao transactionDao;

    private TransactionEntity buildTE() {
        TransactionEntity newT = new TransactionEntity();
        newT.setStatus("PENDING");
        newT.setCurrency(Currency.EUR);
        newT.setAmount(BigInteger.TEN);
        newT.setTransactionId(1);
        newT.setSenderId(2);
        newT.setReceiverId(1);
        return newT;

    }

    @Test
    @DirtiesContext
    void testSuccessfulOperationsInSequence() {
        TransactionEntity newT = buildTE();
        Assertions.assertDoesNotThrow(() -> transactionDao.create(newT));
        Optional<TransactionEntity> queryResult = transactionDao.get(1);
        Assertions.assertTrue(queryResult.isPresent());
        TransactionEntity val = queryResult.get();
        val.setAmount(BigInteger.ONE);
        Assertions.assertDoesNotThrow(() -> transactionDao.update(val, val.getTransactionId()));
        queryResult = transactionDao.get(1);
        Assertions.assertTrue(queryResult.isPresent());
        Assertions.assertEquals(val, queryResult.get());
        Assertions.assertDoesNotThrow(() -> transactionDao.delete(1));
        queryResult = transactionDao.get(1);
        Assertions.assertTrue(queryResult.isEmpty());
    }

    @Test
    @DirtiesContext
    void testFilterOperations() {
        TransactionEntity filter = new TransactionEntity();
        filter.setAmount(BigInteger.valueOf(20301));
        List<TransactionEntity> entities = transactionDao.getFiltered(filter);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("CANCELED", entities.get(0).getStatus());
        Assertions.assertEquals(3, entities.get(0).getSenderId());
        filter.setAmount(null);
        filter.setStatus("COMPLETED");
        entities = transactionDao.getFiltered(filter);
        Assertions.assertEquals(3, entities.size());
        filter.setReceiverId(3);
        entities = transactionDao.getFiltered(filter);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals(BigInteger.valueOf(20010), entities.get(0).getAmount());
        Assertions.assertEquals(Currency.GBP, entities.get(0).getCurrency());
        entities = transactionDao.getFiltered(null);
        Assertions.assertEquals(24, entities.size());
        filter.setTimestamp(Instant.now().minus(2, ChronoUnit.DAYS));
        entities = transactionDao.getFiltered(filter);
        Assertions.assertTrue(entities.isEmpty());
        filter = new TransactionEntity();
        filter.setDescription("Bolletta");
        entities = transactionDao.getFiltered(filter);
        Assertions.assertEquals(15, entities.size());

    }


}
