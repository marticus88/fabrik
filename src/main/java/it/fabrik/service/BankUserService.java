package it.fabrik.service;


import it.fabrik.entity.BankUserEntity;
import it.fabrik.exception.bankUser.BankUserAccountIdException;
import it.fabrik.exception.bankUser.BankUserAlreadyPresentException;
import it.fabrik.repository.BankUserRepository;
import it.fabrik.valueobject.BankUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BankUserService {

    private final ModelMapper modelMapper;
    private final BankUserRepository bankUserRepository;

    public BankUserService(BankUserRepository bankUserRepository) {
        this.bankUserRepository = bankUserRepository;
        this.modelMapper = new ModelMapper();
    }

    public void singup(BankUser bankUser) throws BankUserAccountIdException, BankUserAlreadyPresentException {
        BankUserEntity bankUserEntity = modelMapper.map(bankUser, BankUserEntity.class);

        validateUniqueField(bankUserEntity.getAccountId());

        try {
            bankUserRepository.save(bankUserEntity);
        } catch (Exception e) {
            log.error("User key already present");
            throw new BankUserAlreadyPresentException("BankUser key already present");
        }
    }

    private void validateUniqueField(String accountId) throws BankUserAccountIdException {

        if (bankUserRepository.getBankUserEntityByAccountId(accountId) != null) {
            log.error("Username already presente");
            throw new BankUserAccountIdException("Username already presente");
        }
    }
}
