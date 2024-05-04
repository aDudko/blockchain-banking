package net.dudko.project.service.impl;

import lombok.AllArgsConstructor;
import net.dudko.project.domain.mapper.BlockMapper;
import net.dudko.project.domain.repository.BlockRepository;
import net.dudko.project.model.dto.BlockDto;
import net.dudko.project.model.exceprion.BlockchainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.dudko.project.domain.entity.account.Account;
import net.dudko.project.domain.entity.account.AccountStatus;
import net.dudko.project.domain.entity.transaction.Transaction;
import net.dudko.project.domain.entity.transaction.TransactionType;
import net.dudko.project.domain.mapper.AccountMapper;
import net.dudko.project.domain.mapper.TransactionMapper;
import net.dudko.project.model.exceprion.AccountException;
import net.dudko.project.model.exceprion.BalanceException;
import net.dudko.project.domain.repository.AccountRepository;
import net.dudko.project.domain.repository.TransactionRepository;
import net.dudko.project.model.dto.AccountDto;
import net.dudko.project.model.dto.TransactionDto;
import net.dudko.project.model.dto.TransferFundDto;
import net.dudko.project.service.AccountService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BlockRepository blockRepository;

    private static final int PREFIX = 4;
    private static final String PREFIX_STRING = new String(new char[PREFIX]).replace('\0', '0');

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        var account = AccountMapper.mapToAccount(accountDto);
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto getAccount(Long id) {
        return AccountMapper.mapToAccountDto(getAccountFromDB(id));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        var accounts = accountRepository.findAll();
        return accounts.stream().map((AccountMapper::mapToAccountDto)).toList();
    }

    @Override
    @Transactional
    public AccountDto deposit(Long id, double amount) {
        var account = getAccountFromDB(id);
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("Account isn't active");
        }
        account.setBalance(account.getBalance() + amount);
        var transaction = createTransaction(id, amount, TransactionType.DEPOSIT);
        if (!createBlock(transaction.toString())) {
            transactionRepository.delete(transaction);
            throw new BlockchainException("Block was not added. Operation rejected");
        }
        account = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    @Transactional
    public AccountDto withdraw(Long id, double amount) {
        var account = getAccountFromDB(id);
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("Account isn't active");
        }
        if (account.getBalance() < amount) {
            throw new BalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        var transaction = createTransaction(id, amount, TransactionType.WITHDRAWAL);
        if (!createBlock(transaction.toString())) {
            transactionRepository.delete(transaction);
            throw new BlockchainException("Block was not added. Operation rejected");
        }
        account = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto holdAccount(Long id) {
        var account = getAccountFromDB(id);
        account.setAccountStatus(AccountStatus.HOLD);
        account.setUpdateTime(LocalDateTime.now());
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto deleteAccount(Long id) {
        var account = getAccountFromDB(id);
        account.setAccountStatus(AccountStatus.DELETED);
        account.setUpdateTime(LocalDateTime.now());
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto activateAccount(Long id) {
        var account = getAccountFromDB(id);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setUpdateTime(LocalDateTime.now());
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void transferFunds(TransferFundDto transferFundDto) {
        Account from = getAccountFromDB(transferFundDto.fromAccountId());
        Account to = getAccountFromDB(transferFundDto.toAccountId());
        if (from.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("From-account isn't active");
        }
        if (to.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new AccountException("To-account isn't active");
        }
        if (from.getBalance() < transferFundDto.amount()) {
            throw new BalanceException("Insufficient balance");
        }
        from.setBalance(from.getBalance() - transferFundDto.amount());
        to.setBalance(to.getBalance() + transferFundDto.amount());
        var transaction1 = createTransaction(transferFundDto.fromAccountId(), transferFundDto.amount(), TransactionType.TRANSFER);
        if (!createBlock(transaction1.toString())) {
            transactionRepository.delete(transaction1);
            throw new BlockchainException("Block was not added. Operation rejected");
        }
        var transaction2 = createTransaction(transferFundDto.toAccountId(), transferFundDto.amount(), TransactionType.TRANSFER);
        if (!createBlock(transaction2.toString())) {
            transactionRepository.delete(transaction2);
            throw new BlockchainException("Block was not added. Operation rejected");
        }
        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long id) {
        var transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(id);
        return transactions.stream().map(TransactionMapper::mapToTransactionDto).toList();
    }

    private Account getAccountFromDB(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));
    }

    private Transaction createTransaction(Long id, double amount, TransactionType tractionType) {
        var transaction = Transaction.builder()
                .accountId(id)
                .amount(amount)
                .tractionType(tractionType)
                .timestamp(LocalDateTime.now())
                .build();
        return transactionRepository.save(transaction);

    }

    private boolean createBlock(String data) {
        List<BlockDto> blockchain = blockRepository.findAll().stream().map(BlockMapper::mapToBlockDto).toList();
        if (blockchain.isEmpty()) {
            BlockDto genesisBlock = new BlockDto("The is the Genesis Block.", "0", new Date().getTime());
            genesisBlock.mineBlock(PREFIX);
            blockRepository.save(BlockMapper.mapToBlock(genesisBlock));
            BlockDto firstBlock = new BlockDto("The is the First Block.", genesisBlock.getHash(), new Date().getTime());
            firstBlock.mineBlock(PREFIX);
            blockRepository.save(BlockMapper.mapToBlock(firstBlock));
        }

        boolean flag = true;

        var prevHash = blockRepository.findTopByOrderByIdDesc().getHash();
        BlockDto block = new BlockDto(data, prevHash, new Date().getTime());
        block.mineBlock(PREFIX);

        blockchain = blockRepository.findAll().stream().map(BlockMapper::mapToBlockDto).toList();
        if (!blockchain.isEmpty()) {
            for (int i = 0; i < blockchain.size(); i++) {
                String previousHash = i == 0 ? prevHash : blockchain.get(i - 1).getHash();
                flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash())
                        && previousHash.equals(blockchain.get(i).getPrevHash())
                        && blockchain.get(i).getHash().substring(0, PREFIX).equals(PREFIX_STRING);
            }
        }

        if (flag) {
            blockRepository.save(BlockMapper.mapToBlock(block));
        }

        return flag;
    }

}
