package tech.luau.appsalonmng.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.luau.appsalonmng.domain.FinancialTransaction;
import tech.luau.appsalonmng.repository.FinancialTransactionRepository;
import tech.luau.appsalonmng.service.dto.FinancialTransactionDTO;
import tech.luau.appsalonmng.service.mapper.FinancialTransactionMapper;

/**
 * Service Implementation for managing {@link tech.luau.appsalonmng.domain.FinancialTransaction}.
 */
@Service
@Transactional
public class FinancialTransactionService {

    private final Logger log = LoggerFactory.getLogger(FinancialTransactionService.class);

    private final FinancialTransactionRepository financialTransactionRepository;

    private final FinancialTransactionMapper financialTransactionMapper;

    public FinancialTransactionService(
        FinancialTransactionRepository financialTransactionRepository,
        FinancialTransactionMapper financialTransactionMapper
    ) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.financialTransactionMapper = financialTransactionMapper;
    }

    /**
     * Save a financialTransaction.
     *
     * @param financialTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public FinancialTransactionDTO save(FinancialTransactionDTO financialTransactionDTO) {
        log.debug("Request to save FinancialTransaction : {}", financialTransactionDTO);
        FinancialTransaction financialTransaction = financialTransactionMapper.toEntity(financialTransactionDTO);
        financialTransaction = financialTransactionRepository.save(financialTransaction);
        return financialTransactionMapper.toDto(financialTransaction);
    }

    /**
     * Update a financialTransaction.
     *
     * @param financialTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public FinancialTransactionDTO update(FinancialTransactionDTO financialTransactionDTO) {
        log.debug("Request to update FinancialTransaction : {}", financialTransactionDTO);
        FinancialTransaction financialTransaction = financialTransactionMapper.toEntity(financialTransactionDTO);
        financialTransaction = financialTransactionRepository.save(financialTransaction);
        return financialTransactionMapper.toDto(financialTransaction);
    }

    /**
     * Partially update a financialTransaction.
     *
     * @param financialTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FinancialTransactionDTO> partialUpdate(FinancialTransactionDTO financialTransactionDTO) {
        log.debug("Request to partially update FinancialTransaction : {}", financialTransactionDTO);

        return financialTransactionRepository
            .findById(financialTransactionDTO.getId())
            .map(existingFinancialTransaction -> {
                financialTransactionMapper.partialUpdate(existingFinancialTransaction, financialTransactionDTO);

                return existingFinancialTransaction;
            })
            .map(financialTransactionRepository::save)
            .map(financialTransactionMapper::toDto);
    }

    /**
     * Get all the financialTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FinancialTransactionDTO> findAll() {
        log.debug("Request to get all FinancialTransactions");
        return financialTransactionRepository
            .findAll()
            .stream()
            .map(financialTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one financialTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FinancialTransactionDTO> findOne(Long id) {
        log.debug("Request to get FinancialTransaction : {}", id);
        return financialTransactionRepository.findById(id).map(financialTransactionMapper::toDto);
    }

    /**
     * Delete the financialTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FinancialTransaction : {}", id);
        financialTransactionRepository.deleteById(id);
    }
}
