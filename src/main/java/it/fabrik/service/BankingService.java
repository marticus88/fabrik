package it.fabrik.service;

import it.fabrik.exception.banking.AccountBalanceException;
import it.fabrik.exception.banking.AccountTransactionsException;
import it.fabrik.exception.banking.CreateMoneyTransferException;
import it.fabrik.repository.TransactionRepository;
import it.fabrik.request.banking.CreateMoneyTransferRequest;
import it.fabrik.valueobject.Balance;
import it.fabrik.valueobject.CreateMoneyTransfer;
import it.fabrik.valueobject.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BankingService {

    private final TransactionRepository transactionRepository;
    private final FabrikService fabrikService;
    private final UtilityService utilityService;
    private final TransactionService transactionService;

    public BankingService(FabrikService fabrikService, TransactionRepository transactionRepository, UtilityService utilityService, TransactionService transactionService) {
        this.fabrikService = fabrikService;
        this.transactionRepository = transactionRepository;
        this.utilityService = utilityService;
        this.transactionService = transactionService;
    }

    public Balance accountBalance(String accountId) throws AccountBalanceException {
        return fabrikService.fabrikAccountBalance(accountId);
    }

    public CreateMoneyTransfer createMoneyTransfer(String accountId, CreateMoneyTransferRequest moneyTransferRequest) throws CreateMoneyTransferException {
        return fabrikService.fabrikreateMoneyTransfer(accountId, moneyTransferRequest);
    }

    public List<Transaction> accountTransactions(String accountId, String fromAccountingDate, String toAccountingDate) throws AccountTransactionsException, ParseException {
        Date from = utilityService.stringToDate(fromAccountingDate);
        Date to = utilityService.stringToDate(toAccountingDate);
        List<Transaction> transactions = transactionService.getTransactions(accountId, from, to);
        if (!transactions.isEmpty()) {
            Date firstDate = utilityService.getFirstDate(transactions);
            Date lastDate = utilityService.getLastDate(transactions);
            List<Transaction> transactionsDiffFrom = new ArrayList<>();
            List<Transaction> transactionsDiffTo = new ArrayList<>();
            if (from.before(firstDate)) {
                String diffTo = utilityService.dateToString(utilityService.addDays(firstDate, -1));
                transactionsDiffFrom = fabrikService.fabrikAccountTransactions(accountId, fromAccountingDate, diffTo);
                transactionService.saveTransactions(transactionsDiffFrom, accountId);
                transactions.addAll(transactionsDiffFrom);
            }

            if (to.after(lastDate)) {
                String diffFrom = utilityService.dateToString(utilityService.addDays(lastDate, 1));
                transactionsDiffTo = fabrikService.fabrikAccountTransactions(accountId, diffFrom, toAccountingDate);
                transactionService.saveTransactions(transactionsDiffTo, accountId);
                transactions.addAll(transactionsDiffTo);
            }

        } else {
            transactions = fabrikService.fabrikAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
            transactionService.saveTransactions(transactions, accountId);
        }

        return transactions;
    }
}
