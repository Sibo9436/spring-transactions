package com.sibo.transactions.repository.transaction;

import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.repository.FilterDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionDao implements FilterDao<TransactionEntity, Long> {
    private final JdbcTemplate transactionJdbcTemplate;

    public TransactionDao(JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    @Override
    public Optional<TransactionEntity> get(Long aLong) {
        return Optional.ofNullable(transactionJdbcTemplate.queryForObject("SELECT * FROM TRANSACTIONS T WHERE T.TRANSACTION_ID = ? ", new TransactionRowMapper(), aLong));
    }

    @Override
    public void create(TransactionEntity newT) {
        transactionJdbcTemplate.update("insert into transactions (currency, amount, sender_id, receiver_id, status) values (?,?,?,?)",
                newT.getCurrency(), newT.getAmount(), newT.getSenderId(), newT.getReceiverId(), newT.getStatus());

    }

    @Override
    public void update(TransactionEntity newT) {
        transactionJdbcTemplate.update("UPDATE transactions SET currency=?, amount=?, sender_id=?, receiver_id=? WHERE transaction_id=?",
                newT.getCurrency(), newT.getAmount(), newT.getSenderId(), newT.getReceiverId(), newT.getStatus(),newT.getTransactionID());
    }

    @Override
    public void delete(Long aLong) {
        transactionJdbcTemplate.update("DELETE FROM TRANSACTIONS WHERE transaction_id=?", aLong);

    }

    @Override
    public List<TransactionEntity> getAll() {

        return transactionJdbcTemplate.query("SELECT  * FROM TRANSACTIONS", new TransactionRowMapper());
    }

    @Override
    public List<TransactionEntity> getFiltered(TransactionEntity filter) {
        return null;
    }
}
