package com.company.msproject.service;

import com.company.msproject.entity.Category;
import com.company.msproject.entity.Statistic;
import com.company.msproject.enums.StatusEnum;
import com.company.msproject.repository.StatisticRepository;
import com.company.msproject.service.impl.StatisticServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatisticServiceImplTest {

    private final StatisticRepository statisticRepository = mock(StatisticRepository.class);

    private final StatisticServiceImpl statisticService = new StatisticServiceImpl(statisticRepository);

    private List<Statistic> statistics;

    @BeforeEach
    void setUp() {
        statistics = new ArrayList<>();
        Category category = new Category(1L, "Category A", "Category A", StatusEnum.ACTIVE);
        statistics.add(new Statistic(1L, category, 1L, "Statistic A"));
        statistics.add(new Statistic(2L, category, 1L, "Statistic B"));
    }

    @Test
    void getAll() {
        Page<Statistic> statisticPage = new PageImpl<>(statistics);
        when(statisticRepository.findAll(any(PageRequest.class))).thenReturn(statisticPage);
        var statistics = statisticService.getAll(0, 10);

        assertTrue(statistics.getTotalElements() > 0);
    }
}
