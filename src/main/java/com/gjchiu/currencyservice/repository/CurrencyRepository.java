package com.gjchiu.currencyservice.repository;

import com.gjchiu.currencyservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByOrderByCodeAsc();
    Optional<Currency> findByCode(String code);
    void deleteByCode(String code);
}
