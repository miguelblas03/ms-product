package com.company.msproject.service.impl;

import com.company.msproject.entity.Statistic;
import com.company.msproject.repository.StatisticRepository;
import com.company.msproject.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public Page<Statistic> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return statisticRepository.findAll(pageRequest);
    }
}
