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
    public TransactionDto getTransactionById(Long txId) throws Exception{
        Optional<TransactionEntity> txEntity = transactionRepository.get(txId);
        return txEntity.map(transactionMapper::entityToDto).orElse(new TransactionDto());
    }

    //TODO: qui sarebbe carino mettere un cursore Andrea, vedi di ricordartelo
    /*
    public List<TransactionDto> filterTransactions(TransactionDto filter, PaginationDto pagination) throws Exception{
        return Collections.emptyList();
    }
    public TransactionDto updateTransaction()
    
     */
}
