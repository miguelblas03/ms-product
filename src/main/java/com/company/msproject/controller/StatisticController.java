package com.company.msproject.controller;

import com.company.msproject.dto.StatisticResponseDto;
import com.company.msproject.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.company.msproject.utils.EntityUtils.convertValue;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public Page<StatisticResponseDto> getStatistics(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return statisticService.getAll(pageNumber, pageSize).map(statistic -> convertValue(statistic, StatisticResponseDto.class));
    }
}
