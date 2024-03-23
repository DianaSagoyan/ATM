package atm.service.impl;

import atm.dto.AccountDto;
import atm.entity.Account;
import atm.mapper.MapperUtil;
import atm.repository.AccountRepository;
import atm.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MapperUtil mapperUtil;

    public AccountServiceImpl(AccountRepository accountRepository, MapperUtil mapperUtil) {
        this.accountRepository = accountRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = mapperUtil.convert(accountDto, new Account());
        accountRepository.save(account);
        return accountDto;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        return mapperUtil.convert(account, new AccountDto());
    }
}
