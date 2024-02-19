package com.sibo.transactions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sibo.transactions.dto.TransactionDto;
import com.sibo.transactions.model.Currency;
import com.sibo.transactions.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("unittests")
@ComponentScan("com.sibo")
class TransactionsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Nested
    @AutoConfigureMockMvc
    class TransactionsControllerTests {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private TransactionService transactionService;

        @Test
        void testListTransactions_OK() throws Exception {
            Mockito.when(transactionService.filter(Mockito.any())).thenReturn(buildTransactions());
            mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
                    .andExpectAll(
                            MockMvcResultMatchers.status().isOk(),
                            result -> {
                                String body = result.getResponse().getContentAsString();
                                Assertions.assertNotNull(body);
                                List<TransactionDto> resultList = new ObjectMapper().readValue(body, new TypeReference<List<TransactionDto>>() {});
                                Assertions.assertEquals(resultList.size(), 3);
                            }
                    );
        }

        @Test
        void testGetTransactionById_OK() throws Exception {
            TransactionDto transaction = createTransactionDto(1, BigInteger.TEN, Currency.USD, 1, 2);
            Mockito.when(transactionService.getTransactionById(1)).thenReturn(Optional.of(transaction));
            mockMvc.perform(MockMvcRequestBuilders.get("/transactions/1")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(result -> {
                        String body = result.getResponse().getContentAsString();
                        Assertions.assertNotNull(body);
                        TransactionDto res = new ObjectMapper().readValue(body, TransactionDto.class);
                        Assertions.assertEquals(transaction, res);
                    });
        }
        @Test
        void testGetTransactionById_NotFound() throws Exception {
            Mockito.when(transactionService.getTransactionById(1)).thenReturn(Optional.empty());
            mockMvc.perform(MockMvcRequestBuilders.get("/transactions/1"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
        @Test
        void testDeleteTransactionById_OK() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testUpdateTransaction_OK() throws Exception {
            TransactionDto transaction = createTransactionDto(1, BigInteger.TEN, Currency.EUR, 1, 2);
            mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsBytes(transaction)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }
        @Test
        void testInsertTransaction_OK() throws Exception {
            TransactionDto transaction = createTransactionDto(1, BigInteger.TEN, Currency.EUR, 1, 2);
            mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsBytes(transaction)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }

        private TransactionDto createTransactionDto(int transactionId, BigInteger amount, Currency currency, int senderId, int receiverId) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(BigInteger.TEN);
            transactionDto.setCurrency(Currency.EUR);
            transactionDto.setSenderId(1);
            transactionDto.setReceiverId(2);
            return transactionDto;
        }
        private List<TransactionDto> buildTransactions() {
            List<TransactionDto> list = new ArrayList<>();
            list.add(createTransactionDto(0, BigInteger.TEN, Currency.USD, 1, 2));
            list.add(createTransactionDto(1, BigInteger.TEN, Currency.GBP, 1, 4));
            list.add(createTransactionDto(2, BigInteger.TEN, Currency.EUR, 3, 2));
            return list;
        }
    }


}
