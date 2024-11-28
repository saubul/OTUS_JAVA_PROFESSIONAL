package ru.otus.bank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.bank.entity.Account;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    AccountDao accountDao;

    @BeforeEach
    void init() {
        accountDao = new AccountDao();
    }

    @Test
    void findByAgreementId() {
        Account account1 = new Account();
        account1.setAgreementId(1L);
        Account account2 = new Account();
        account2.setAgreementId(2L);
        accountDao.accountMap.put(1L, account1);
        accountDao.accountMap.put(2L, account2);

        Iterable<Account> accounts = accountDao.findByAgreementId(1L);
        Iterator<Account> accountIterator = accounts.iterator();
        assertTrue(accountIterator.hasNext());
        assertEquals(1L, accountIterator.next().getAgreementId());
        assertFalse(accountIterator.hasNext());
    }

    @Test
    void findByIdReturnsOptionalEmpty() {
        assertEquals(Optional.empty(), accountDao.findById(1L));
    }

    @Test
    void findByIdReturnsValue() {
        Account account = new Account();
        accountDao.accountMap.put(1L, account);
        Optional<Account> foundAccount = accountDao.findById(1L);
        assertTrue(foundAccount.isPresent());
        assertEquals(account, foundAccount.get());
    }

    @Test
    void saveAccountWithNullId() {
        Account account = new Account();
        account.setNumber("999");
        long idBefore = accountDao.id.get();
        accountDao.save(account);
        long idAfter = accountDao.id.get();

        assertEquals(idBefore, idAfter, 1);
        assertEquals(account, accountDao.accountMap.get(2L));
        assertEquals(2L, account.getId());
    }

    @Test
    void findAll() {
        assertFalse(accountDao.findAll().iterator().hasNext());
    }
}