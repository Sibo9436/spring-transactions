package com.sibo.transactions.mapper;

import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public interface TransactionMapper {
    TransactionDto entityToDto(TransactionEntity entity);

    TransactionEntity dtoToEntity(TransactionDto dto);

}
