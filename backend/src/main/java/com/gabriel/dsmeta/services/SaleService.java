package com.gabriel.dsmeta.services;

import com.gabriel.dsmeta.entities.Sale;
import com.gabriel.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;

    public Page<Sale> findSales(String minDate, String maxDate, Pageable pageable){
        LocalDate min, max;
        if (isDataParamEmpty(minDate, maxDate)){
            min = LocalDate.parse(minDate);
            max = LocalDate.parse(maxDate);
        } else {
            max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            min = max.minusYears(1);
        }
        return repository.findSales(min, max, pageable);
    }

    private boolean isDataParamEmpty(String minDate, String maxDate) {
        return minDate.contentEquals("") || maxDate.contentEquals("") ? false : true;
    }
}
