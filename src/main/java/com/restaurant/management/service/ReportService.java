package com.restaurant.management.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {
    double getTotalSalesByDate(LocalDate date);
    Map<String, Double> getSalesByCategory(LocalDate date);
    int getTotalOrdersByDate(LocalDate date);
}
