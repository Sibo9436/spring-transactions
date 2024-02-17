package com.sibo.transactions.repository.transaction;

import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.model.Currency;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionRowMapper implements RowMapper<TransactionEntity> {
    @Override
    public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException,IllegalArgumentException {
        TransactionEntity te = new TransactionEntity();
        te.setTransactionID(rs.getLong("TRANSACTION_ID"));
        //Se popoliamo correttamente il db non dovremmo mai avere il problema della valuta non valida
        te.setCurrency(Currency.valueOf(rs.getString("CURRENCY")));
        te.setAmount(BigInteger.valueOf(rs.getLong("AMOUNT")));
        te.setTimestamp(rs.getTimestamp("TRANSACTION_TIME").toLocalDateTime());
        te.setSenderId(rs.getLong("SENDER_ID"));
        te.setSenderId(rs.getLong("RECEIVER_ID"));
        te.setStatus(rs.getString("STATUS"));
        return te;
    }
}
