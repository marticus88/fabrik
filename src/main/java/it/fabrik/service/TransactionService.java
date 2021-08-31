package it.fabrik.service;

import it.fabrik.entity.TransactionEntity;
import it.fabrik.repository.TransactionRepository;
import it.fabrik.valueobject.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UtilityService utilityService;


    public TransactionService(TransactionRepository transactionRepository, UtilityService utilityService) {
        this.transactionRepository = transactionRepository;
        this.utilityService = utilityService;
    }

    public List<Transaction> getTransactions(String accountId, Date from, Date to) {
        List<TransactionEntity> transactions = transactionRepository.findAllByAccountIdAndSearchDateBetween(accountId, from, to);
        return utilityService.transactionsMarshal(transactions);
    }

    public void saveTransactions(List<Transaction> transactions, String accountId) {
        List<TransactionEntity> transactionEntities = utilityService.transactionEntitiesBuilder(transactions, accountId);
        transactionRepository.saveAll(transactionEntities);
    }
}
