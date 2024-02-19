package com.sibo.transactions.service;

import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.entity.TransactionEntity;
import com.sibo.transactions.mapper.TransactionMapper;
import com.sibo.transactions.repository.transaction.TransactionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {
    private final TransactionDao transactionRepository;
    private final TransactionMapper transactionMapper;
    public TransactionService(TransactionDao repo, TransactionMapper transactionMapper){
        this.transactionRepository = repo;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionDto> getTransactions() throws Exception{
        return transactionRepository.getAll().stream().map(transactionMapper::entityToDto).collect(Collectors.toList());
    }
    public Optional<TransactionDto> getTransactionById(Integer txId) throws Exception{
        Optional<TransactionEntity> txEntity = transactionRepository.get(txId);
        return txEntity.map(transactionMapper::entityToDto);
    }

    public void addTransaction(TransactionDto dto) throws Exception {
        TransactionEntity entity = transactionMapper.dtoToEntity(dto);
        transactionRepository.create(entity);
    }

    public void updateTransaction(TransactionDto dto, int transactionId) throws Exception{
        TransactionEntity entity = transactionMapper.dtoToEntity(dto);
        transactionRepository.update(entity, transactionId);
    }

    public void deleteTransaction(int transactionId) throws Exception{
        transactionRepository.delete(transactionId);
    }

    public List<TransactionDto> filter(TransactionDto params) {
        List<TransactionEntity> filtered = transactionRepository.getFiltered(transactionMapper.dtoToEntity(params));
        return filtered.stream().map(transactionMapper::entityToDto).collect(Collectors.toList());
    }

}
