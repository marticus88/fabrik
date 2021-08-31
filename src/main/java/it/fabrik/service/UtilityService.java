package it.fabrik.service;

import it.fabrik.entity.TransactionEntity;
import it.fabrik.valueobject.Transaction;
import it.fabrik.valueobject.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UtilityService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public Date stringToDate(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    private TransactionEntity transactionEntityBuilder(Transaction transaction, String accountId) {
        return TransactionEntity.builder()
                .accountId(accountId)
                .transactionId(transaction.getTransactionId())
                .accountingDate(transaction.getAccountingDate())
                .operationId(transaction.getOperationId())
                .searchDate(stringToDate(transaction.getAccountingDate()))
                .valueDate(transaction.getValueDate())
                .typeEnumeration(transaction.getType().getEnumeration())
                .typeValue(transaction.getType().getValue())
                .build();
    }

    private Transaction transactionMarshal(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .accountId(transactionEntity.getAccountId())
                .transactionId(transactionEntity.getTransactionId())
                .accountingDate(transactionEntity.getAccountingDate())
                .operationId(transactionEntity.getOperationId())
                .searchDate(transactionEntity.getSearchDate())
                .valueDate(transactionEntity.getValueDate())
                .type(Type.builder()
                        .enumeration(transactionEntity.getTypeEnumeration())
                        .value(transactionEntity.getTypeValue())
                        .build())
                .build();
    }

    public List<TransactionEntity> transactionEntitiesBuilder(List<Transaction> transactions, String accountId) {
        return transactions.stream().map(transaction -> transactionEntityBuilder(transaction, accountId)).collect(Collectors.toList());
    }

    public List<Transaction> transactionsMarshal(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream().map(this::transactionMarshal).collect(Collectors.toList());
    }

    public Date getFirstDate(List<Transaction> transactions) {
        Date firstDate = transactions.stream().findFirst().get().getSearchDate();
        for (Transaction t : transactions) {
            if (t.getSearchDate().before(firstDate)) {
                firstDate = t.getSearchDate();
            }
        }

        return firstDate;
    }

    public Date getLastDate(List<Transaction> transactions) {
        Date lastDate = transactions.stream().findFirst().get().getSearchDate();
        for (Transaction t : transactions) {
            if (t.getSearchDate().after(lastDate)) {
                lastDate = t.getSearchDate();
            }
        }
        return lastDate;
    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

}
