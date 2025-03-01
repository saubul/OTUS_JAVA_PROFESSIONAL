package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.metrics.SuccessTransfersMetricsService;
import ru.otus.java.pro.mt.core.transfers.metrics.UnsuccessTransfersMetricsService;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final LimitsServiceImpl limitsService;
    private final SuccessTransfersMetricsService successTransfersMetricsService;
    private final UnsuccessTransfersMetricsService unsuccessTransfersMetricsService;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 0) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        } else if (pageSize < 20) {
            pageSize = 20;
        } else if (pageSize > 1000) {
            pageSize = 1000;
        }

        return transfersRepository.findAllByClientId(clientId, PageRequest.of(pageNum, pageSize));
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        try {
            transferRequestValidator.validate(executeTransferDtoRq);
            // execution
            if (!limitsService.isLimitEnough()) {
                // ...
            }
            if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
                throw new BusinessLogicException("OOPS", "OOPS_CODE");
            }
            Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
            save(transfer);
            successTransfersMetricsService.increment();
        } catch (Exception e) {
            unsuccessTransfersMetricsService.increment();
            throw e;
        }
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }
}
