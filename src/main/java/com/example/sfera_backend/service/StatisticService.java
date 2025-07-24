package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StatisticDTO;

public interface StatisticService {
    ApiResponse<StatisticDTO> getStatistic();
}
