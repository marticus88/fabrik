package it.fabrik.service;

import it.fabrik.exception.banking.AccountBalanceException;
import it.fabrik.exception.banking.AccountTransactionsException;
import it.fabrik.exception.banking.CreateMoneyTransferException;
import it.fabrik.fabrikServiceResponse.FabrikBalanceResponse;
import it.fabrik.fabrikServiceResponse.FabrikCreateMoneyTransferResponse;
import it.fabrik.request.banking.CreateMoneyTransferRequest;
import it.fabrik.valueobject.Balance;
import it.fabrik.valueobject.CreateMoneyTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Service
public class FabrikService {


    @Value("${fabrik.accountBalance.url}")
    private String fabrikAccontBalanceUrl;

    @Value("${fabrik.createMoneyTransfer.url}")
    private String fabrikCreateMoneyTransferUrl;

    @Value("${fabrik.accountTransactions.url}")
    private String fabrikAccountTransactionsUrl;

    @Value("${fabrik.auth.schema}")
    private String fabrikAuthSchema;

    @Value("${fabrik.api.key}")
    private String fabrikApiKey;

    public Balance fabrikAccountBalance(String accountId) throws AccountBalanceException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", fabrikAuthSchema);
        headers.set("Api-Key", fabrikApiKey);

        HttpEntity request = new HttpEntity(headers);
        FabrikBalanceResponse resp;
        try {
            resp = restTemplate.exchange(
                    fabrikAccontBalanceUrl,
                    HttpMethod.GET,
                    request,
                    FabrikBalanceResponse.class,
                    accountId
            ).getBody();
        } catch (Exception e) {
            log.error("Fabik accountBalance Exception {}", e.getMessage());
            throw new AccountBalanceException("Fabik accountBalance error");
        }

        if (!HttpStatus.OK.getReasonPhrase().equals(resp.getStatus())) {
            log.error("Fabik accountBalance error {}", resp.getErrors().toString());
            throw new AccountBalanceException("Fabik accountBalance error");
        }

        return resp.getPayload();
    }

    public CreateMoneyTransfer fabrikreateMoneyTransfer(String accountId, CreateMoneyTransferRequest moneyTransferRequest) throws CreateMoneyTransferException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", fabrikAuthSchema);
        headers.set("Api-Key", fabrikApiKey);

        HttpEntity request = new HttpEntity(moneyTransferRequest, headers);
        FabrikCreateMoneyTransferResponse resp;
        try {
            resp = restTemplate.exchange(
                    fabrikCreateMoneyTransferUrl,
                    HttpMethod.POST,
                    request,
                    FabrikCreateMoneyTransferResponse.class,
                    accountId
            ).getBody();
        } catch (Exception e) {
            log.error("Fabik createMoneyTransfer Exception {}", e.getMessage());
            throw new CreateMoneyTransferException("Fabik createMoneyTransfer error");
        }

        if (!HttpStatus.OK.getReasonPhrase().equals(resp.getStatus())) {
            log.error("Fabik accountBalance error {}", resp.getErrors().toString());
            throw new CreateMoneyTransferException("Fabik createMoneyTransfer error");
        }

        return resp.getPayload();
    }

    public CreateMoneyTransfer fabrikAccountTransactions(String accountId, String fromAccountingDate, String toAccountingDate) throws AccountTransactionsException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", fabrikAuthSchema);
        headers.set("Api-Key", fabrikApiKey);

        HttpEntity request = new HttpEntity(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fabrikAccountTransactionsUrl)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate);

        FabrikCreateMoneyTransferResponse resp;
        try {
            resp = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    FabrikCreateMoneyTransferResponse.class,
                    accountId
            ).getBody();
        } catch (Exception e) {
            log.error("Fabik accountTransactions Exception {}", e.getMessage());
            throw new AccountTransactionsException("Fabik accountTransactions error");
        }

        if (!HttpStatus.OK.getReasonPhrase().equals(resp.getStatus())) {
            log.error("Fabik accountBalance error {}", resp.getErrors().toString());
            throw new AccountTransactionsException("Fabik accountTransactions error");
        }

        return resp.getPayload();
    }

}
