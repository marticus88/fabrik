package it.fabrik.service;

import it.fabrik.exception.banking.AccountBalanceException;
import it.fabrik.exception.banking.AccountTransactionsException;
import it.fabrik.exception.banking.CreateMoneyTransferException;
import it.fabrik.repository.TransactionRepository;
import it.fabrik.request.banking.CreateMoneyTransferRequest;
import it.fabrik.valueobject.Balance;
import it.fabrik.valueobject.CreateMoneyTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class BankingService {

    private final TransactionRepository transactionRepository;
    private final FabrikService fabrikService;

    public BankingService(FabrikService fabrikService, TransactionRepository transactionRepository) {
        this.fabrikService = fabrikService;
        this.transactionRepository = transactionRepository;
    }

    public Balance accountBalance(String accountId) throws AccountBalanceException {
        return fabrikService.fabrikAccountBalance(accountId);
    }

    public CreateMoneyTransfer createMoneyTransfer(String accountId, CreateMoneyTransferRequest moneyTransferRequest) throws CreateMoneyTransferException {
        return fabrikService.fabrikreateMoneyTransfer(accountId, moneyTransferRequest);
    }

    public CreateMoneyTransfer accountTransactions(String accountId, String fromAccountingDate, String toAccountingDate) throws AccountTransactionsException {
        transactionRepository.findAllByAccountIdAndAccountingDateBetween(accountId, new Date(), new Date());
        return fabrikService.fabrikAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
    }
}
