package atm.service.impl;

import atm.dto.AccountDto;
import atm.entity.Account;
import atm.mapper.MapperUtil;
import atm.repository.AccountRepository;
import atm.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return mapperUtil.convert(account, new AccountDto());
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        Double currentBalance = account.getBalance();
        if(amount > currentBalance) throw new RuntimeException("You do not have enough on the balance");
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        return mapperUtil.convert(account, new AccountDto());
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> mapperUtil.convert(account, new AccountDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        accountRepository.deleteById(id);
    }
}
