package it.fabrik.repository;

import it.fabrik.entity.BankUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BankUserRepository extends CrudRepository<BankUserEntity, Integer> {

    BankUserEntity getBankUserEntityByAccountIdAndPassword(@Param("username") String username, @Param("password") String password);

    BankUserEntity getBankUserEntityByAccountId(@Param("username") String accountId);


}
