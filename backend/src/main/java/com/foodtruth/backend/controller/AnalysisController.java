package com.foodtruth.backend.controller;

import com.foodtruth.backend.dto.AnalysisRequest;
import com.foodtruth.backend.dto.AnalysisResponse;
import com.foodtruth.backend.service.FoodProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final FoodProductService foodProductService;

    public AnalysisController(FoodProductService foodProductService) {
        this.foodProductService = foodProductService;
    }

    @PostMapping("/products/{productId}/analyses")
    public AnalysisResponse createAnalysis(
            @PathVariable Long productId,
            @RequestBody AnalysisRequest request) {

        return foodProductService.createAnalysis(productId, request);
    }

    @GetMapping("/products/{productId}/analyses")
    public List<AnalysisResponse> getAnalyses(
            @PathVariable Long productId) {

        return foodProductService.getAnalyses(productId);
    }

    @GetMapping("/analyses/{analysisId}")
    public AnalysisResponse getAnalysis(
            @PathVariable Long analysisId) {

        return foodProductService.getAnalysisById(analysisId);
    }
}