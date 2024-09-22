package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Test
    void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }


    @Test
    void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));

        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
    }

    @Test
    void iterableToListTest() {
        Account account = new Account();
        account.setId(2L);
        when(accountDao.findAll()).thenReturn(List.of(account));
        List<Account> accounts = accountServiceImpl.getAccounts();

        verify(accountDao, times(1)).findAll();
        assertEquals(1, accounts.size());
        assertEquals(2L, accounts.get(0).getId());
    }

    @Test
    void getAccountsTest() {
        when(accountDao.findByAgreementId(any())).thenReturn(List.of(new Account(), new Account()));
        assertEquals(2, accountServiceImpl.getAccounts(new Agreement()).size());
        verify(accountDao, times(1)).findByAgreementId(any());
    }

    @Test
    void chargeNoSourceAccountExceptionTest() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> accountServiceImpl.charge(any(), null));
    }

    @Test
    void chargeTrueTest() {
        Account account = new Account();
        account.setAmount(new BigDecimal(10));
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));

        assertTrue(accountServiceImpl.charge(1L, new BigDecimal(10)));
        assertEquals(new BigDecimal("0"), account.getAmount());
        verify(accountDao, times(1)).save(any());
        verify(accountDao, times(1)).save(account);
    }

    @Test
    void addAccountTest() {
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        Agreement agreement = new Agreement();
        agreement.setId(999L);
        String accountNumber = "9999810";
        Integer type = 0;
        BigDecimal amount = new BigDecimal(10);

        accountServiceImpl.addAccount(agreement, accountNumber, type, amount);
        verify(accountDao, times(1)).save(any());

        verify(accountDao).save(accountArgumentCaptor.capture());
        Account account = accountArgumentCaptor.getValue();
        assertEquals(agreement.getId(), account.getAgreementId());
        assertEquals(accountNumber, account.getNumber());
        assertEquals(type, account.getType());
        assertEquals(amount, account.getAmount());
    }
}
