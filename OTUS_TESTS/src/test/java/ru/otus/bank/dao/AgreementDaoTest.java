package ru.otus.bank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.bank.entity.Agreement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class AgreementDaoTest {

    AgreementDao agreementDao;

    @BeforeEach
    void init() {
        agreementDao = new AgreementDao();
    }

    @Test
    void addAgreement() {
        Agreement agreement = agreementDao.addAgreement("test");

        assertEquals(agreement.getName(), "test");
        assertEquals(1, agreementDao.agreementMap.size());
    }

    @Test
    void findByNameTrue() {
        Agreement agreement1 = new Agreement();
        agreement1.setName("test1");
        Agreement agreement2 = new Agreement();
        agreement2.setName("test2");
        agreementDao.agreementMap.put(1L, agreement1);
        agreementDao.agreementMap.put(2L, agreement2);

        Optional<Agreement> foundAgreement = agreementDao.findByName("test1");
        assertTrue(foundAgreement.isPresent());
        assertEquals("test1", foundAgreement.get().getName());
    }

    @Test
    void findByNameFalse() {
        Agreement agreement1 = new Agreement();
        agreement1.setName("test1");
        agreementDao.agreementMap.put(1L, agreement1);

        Optional<Agreement> foundAgreement2 = agreementDao.findByName("test3");
        assertTrue(foundAgreement2.isEmpty());
    }

    @Test
    void saveNullIdAgreement() {
        Agreement agreement = new Agreement();
        long idBefore = agreementDao.id.get();
        agreementDao.save(agreement);
        long idAfter = agreementDao.id.get();

        assertEquals(idBefore, idAfter, 1);
        assertEquals(1, agreementDao.agreementMap.size());
    }

    @Test
    void saveNotNullIdAgreement() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreementDao.save(agreement);

        assertEquals(1, agreementDao.agreementMap.size());
        assertEquals(agreement, agreementDao.agreementMap.get(agreement.getId()));
    }
}