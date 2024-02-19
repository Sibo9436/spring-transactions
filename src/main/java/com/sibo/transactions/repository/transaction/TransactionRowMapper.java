package com.sibo.transactions.repository.transaction;

import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


public class TransactionRowMapper implements RowMapper<TransactionEntity> {
    @Override
    public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException,IllegalArgumentException {
        TransactionEntity te = new TransactionEntity();
        te.setTransactionId(rs.getInt("TRANSACTION_ID"));
        //Se popoliamo correttamente il db non dovremmo mai avere il problema della valuta non valida
        te.setCurrency(Currency.valueOf(rs.getString("CURRENCY")));
        te.setAmount(BigInteger.valueOf(rs.getLong("AMOUNT")));
        Optional<Timestamp> ot = Optional.ofNullable(rs.getTimestamp("TRANSACTION_TIME"));
        te.setTimestamp(ot.map(Timestamp::toInstant).orElse(null));
        te.setSenderId(rs.getInt("SENDER_ID"));
        te.setReceiverId(rs.getInt("RECEIVER_ID"));
        te.setStatus(rs.getString("STATUS"));
        te.setDescription(rs.getString("DESCRIPTION"));
        return te;
    }
}
