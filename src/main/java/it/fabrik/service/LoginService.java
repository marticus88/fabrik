package it.fabrik.service;


import it.fabrik.entity.BankUserEntity;
import it.fabrik.exception.LoginException;
import it.fabrik.repository.BankUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LoginService {

    private final BankUserRepository bankUserRepository;
    private final JwtService jwtService;


    public LoginService(JwtService jwtService, BankUserRepository bankUserRepository) {
        this.jwtService = jwtService;
        this.bankUserRepository = bankUserRepository;
    }

    public String login(String accoutId, String password) throws LoginException {
        BankUserEntity bankUserEntity = bankUserRepository.getBankUserEntityByAccountIdAndPassword(accoutId, password);
        if (bankUserEntity == null) {
            log.error("AccountId and password missmatch");
            throw new LoginException("AccountId and password missmatch");
        }
        return jwtService.generateToken(bankUserEntity.getAccountId());
    }
}
