package it.fabrik.repository;

import it.fabrik.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findAllByAccountIdAndSearchDateBetween(String accountId, Date from, Date to);
}
