package com.company.msproject.service;

import com.company.msproject.entity.Statistic;
import org.springframework.data.domain.Page;

public interface StatisticService {

    Page<Statistic> getAll(int pageNumber, int pageSize);
}
