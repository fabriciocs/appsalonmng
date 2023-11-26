package tech.luau.appsalonmng.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.luau.appsalonmng.domain.StockHistory;
import tech.luau.appsalonmng.repository.StockHistoryRepository;
import tech.luau.appsalonmng.service.dto.StockHistoryDTO;
import tech.luau.appsalonmng.service.mapper.StockHistoryMapper;

/**
 * Service Implementation for managing {@link tech.luau.appsalonmng.domain.StockHistory}.
 */
@Service
@Transactional
public class StockHistoryService {

    private final Logger log = LoggerFactory.getLogger(StockHistoryService.class);

    private final StockHistoryRepository stockHistoryRepository;

    private final StockHistoryMapper stockHistoryMapper;

    public StockHistoryService(StockHistoryRepository stockHistoryRepository, StockHistoryMapper stockHistoryMapper) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockHistoryMapper = stockHistoryMapper;
    }

    /**
     * Save a stockHistory.
     *
     * @param stockHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public StockHistoryDTO save(StockHistoryDTO stockHistoryDTO) {
        log.debug("Request to save StockHistory : {}", stockHistoryDTO);
        StockHistory stockHistory = stockHistoryMapper.toEntity(stockHistoryDTO);
        stockHistory = stockHistoryRepository.save(stockHistory);
        return stockHistoryMapper.toDto(stockHistory);
    }

    /**
     * Update a stockHistory.
     *
     * @param stockHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public StockHistoryDTO update(StockHistoryDTO stockHistoryDTO) {
        log.debug("Request to update StockHistory : {}", stockHistoryDTO);
        StockHistory stockHistory = stockHistoryMapper.toEntity(stockHistoryDTO);
        stockHistory = stockHistoryRepository.save(stockHistory);
        return stockHistoryMapper.toDto(stockHistory);
    }

    /**
     * Partially update a stockHistory.
     *
     * @param stockHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StockHistoryDTO> partialUpdate(StockHistoryDTO stockHistoryDTO) {
        log.debug("Request to partially update StockHistory : {}", stockHistoryDTO);

        return stockHistoryRepository
            .findById(stockHistoryDTO.getId())
            .map(existingStockHistory -> {
                stockHistoryMapper.partialUpdate(existingStockHistory, stockHistoryDTO);

                return existingStockHistory;
            })
            .map(stockHistoryRepository::save)
            .map(stockHistoryMapper::toDto);
    }

    /**
     * Get all the stockHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StockHistoryDTO> findAll() {
        log.debug("Request to get all StockHistories");
        return stockHistoryRepository.findAll().stream().map(stockHistoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one stockHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StockHistoryDTO> findOne(Long id) {
        log.debug("Request to get StockHistory : {}", id);
        return stockHistoryRepository.findById(id).map(stockHistoryMapper::toDto);
    }

    /**
     * Delete the stockHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StockHistory : {}", id);
        stockHistoryRepository.deleteById(id);
    }
}
