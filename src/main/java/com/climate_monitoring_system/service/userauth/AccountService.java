package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.dto.userauth.AccountDTO;
import com.climate_monitoring_system.repository.userauth.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDTO getAccountById(long id) {
        Optional<Account> account = accountRepository.findById(id);
        return checkIfAccountPresentAndReturnAccountDTO(account);
    }

    public AccountDTO getAccountByAccountType(String accountType) {
        Optional<Account> account = accountRepository.findByAccountType(accountType);
        return checkIfAccountPresentAndReturnAccountDTO(account);
    }

    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            accountDTOs.add(getAccountDTO(account));
        }

        return accountDTOs;
    }

    private AccountDTO checkIfAccountPresentAndReturnAccountDTO(Optional<Account> optionalAccount) {
        return optionalAccount.map(this::getAccountDTO).orElseGet(AccountDTO::new);
    }

    private AccountDTO getAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(account.getAccountId());
        accountDTO.setAccountType(account.getAccountType());
        return accountDTO;
    }
}
