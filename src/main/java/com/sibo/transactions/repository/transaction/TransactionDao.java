package com.sibo.transactions.repository.transaction;

import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.repository.FilterDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionDao implements FilterDao<TransactionEntity, Integer> {

    public static final String SELECT_ALL = "SELECT * FROM accenture.TRANSACTION";
    public static final String COND_TRANSACTION_ID = "transaction_id = :transactionId";
    public static final String COND_SENDER_ID = "sender_id = ?";
    public static final String COND_RECV_ID = "receiver_id = ?";
    public static final String COND_CURRENCY = "currency = ?";
    public static final String COND_DAY = "date_trunc('day', transaction_time) = ?";
    public static final String COND_STATUS = "status = ?";
    private static final String COND_AMOUNT = "amount = ?";
    private static final String COND_DESCRIPTION = "description ILIKE ?" ;
    private final JdbcTemplate transactionJdbcTemplate;

    public TransactionDao(JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    @Override
    public Optional<TransactionEntity> get(Integer aInteger) {
        try {
            TransactionEntity result = transactionJdbcTemplate.queryForObject("SELECT * FROM accenture.TRANSACTION T WHERE T.TRANSACTION_ID = ? "
                    , new TransactionRowMapper(), aInteger);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            log.info("No transactions found for id: [{}]", aInteger);
            return Optional.empty();
        }
    }

    @Override
    public void create(TransactionEntity newT) {
        transactionJdbcTemplate.update("INSERT INTO accenture.transaction (currency, amount, sender_id, receiver_id, status,description) VALUES (?,?,?,?,?,?);",
                newT.getCurrency().toString(), newT.getAmount(), newT.getSenderId(), newT.getReceiverId(), newT.getStatus(),newT.getDescription());

    }

    @Override
    @Transactional
    public void update(TransactionEntity newT, Integer txId ) {
        int affectedRows = transactionJdbcTemplate.update("UPDATE accenture.transaction SET currency=?, amount=?, sender_id=?, receiver_id=?,status=?, description=? WHERE transaction_id=?",
                newT.getCurrency().toString(), newT.getAmount(), newT.getSenderId(), newT.getReceiverId(), newT.getStatus(), newT.getDescription(),txId);
        if (affectedRows > 1){
            log.error("Update affected more than one row, rolling back, id [{}]", txId);
            throw new RuntimeException("An error occurred while updating the transaction" );
        }
    }

    @Override
    @Transactional
    public void delete(Integer txId) {
        int affectedRows = transactionJdbcTemplate.update("DELETE FROM accenture.TRANSACTION WHERE transaction_id=?", txId);
        if (affectedRows > 1) {
            log.error("Delete affected more than one row, rolling back, id [{}]", txId);
            throw new RuntimeException("An error occurred while deleting the transaction");
        }
    }

    @Override
    public List<TransactionEntity> getAll() {
        return transactionJdbcTemplate.query("SELECT  * FROM accenture.TRANSACTION", new TransactionRowMapper());
    }

    @Override
    public List<TransactionEntity> getFiltered(TransactionEntity filter) {
        if (filter == null) {
            return getAll();
        }
        List<Object> parameters = new ArrayList<>();
        List<String> query = new ArrayList<>();
        if (filter.getTransactionId() != null) {
            parameters.add(filter.getTransactionId());
            query.add(COND_TRANSACTION_ID);
        }
        if (filter.getTimestamp() != null) {
            Timestamp ts = Timestamp.from(filter.getTimestamp().truncatedTo(ChronoUnit.DAYS));
            parameters.add(ts);
            query.add(COND_DAY);
        }
        if (filter.getStatus() != null) {
            parameters.add(filter.getStatus());
            query.add(COND_STATUS);
        }
        if (filter.getSenderId() != null) {
            parameters.add(filter.getSenderId());
            query.add(COND_SENDER_ID);
        }
        if (filter.getReceiverId() != null) {
            parameters.add(filter.getReceiverId());
            query.add(COND_RECV_ID);
        }
        if (filter.getCurrency() != null) {
            parameters.add(filter.getCurrency().toString());
            query.add(COND_CURRENCY);
        }
        if (filter.getAmount() != null) {
            parameters.add(filter.getAmount());
            query.add(COND_AMOUNT);
        }
        if (filter.getDescription() != null) {
            parameters.add("%"+filter.getDescription()+ "%");
            query.add(COND_DESCRIPTION);
        }
        if (parameters.isEmpty()) {
            return getAll();
        }
        String qry = SELECT_ALL + " WHERE " + String.join(" AND ", query);
        return transactionJdbcTemplate.queryForStream(qry, new TransactionRowMapper(), parameters.toArray()).collect(Collectors.toList());
    }
}
