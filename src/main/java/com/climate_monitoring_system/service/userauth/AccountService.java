package com.climate_monitoring_system.service.userauth;

import com.climate_monitoring_system.domain.userauth.Account;
import com.climate_monitoring_system.dto.userauth.AccountDTO;
import com.climate_monitoring_system.repository.userauth.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDTO getAccountById(long id) {
        Optional<Account> account = accountRepository.findById(id);

        return getAccountDTO(account);
    }

    public AccountDTO getAccountByAccountType(String accountType) {
        Optional<Account> account = accountRepository.findByAccountType(accountType);

        return getAccountDTO(account);
    }

    private AccountDTO getAccountDTO(Optional<Account> account) {
        if (account.isPresent()) {
            Account foundAccount = account.get();

            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountId(foundAccount.getAccountId());
            accountDTO.setAccountType(foundAccount.getAccountType());
            return accountDTO;
        }

        return new AccountDTO();
    }
}
