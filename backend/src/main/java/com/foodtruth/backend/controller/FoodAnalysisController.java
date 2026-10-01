package com.foodtruth.backend.controller;

import com.foodtruth.backend.dto.AnalyzeRequest;
import com.foodtruth.backend.dto.AnalyzeResponse;
import com.foodtruth.backend.dto.ErrorResponse;
import com.foodtruth.backend.service.FoodAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for food analysis.
 * Delegates all business logic to FoodAnalysisService.
 */
@RestController
@RequestMapping("/api")
public class FoodAnalysisController {

    private final FoodAnalysisService foodAnalysisService;

    public FoodAnalysisController(FoodAnalysisService foodAnalysisService) {
        this.foodAnalysisService = foodAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(@Valid @RequestBody AnalyzeRequest request) {
        if (!request.hasImage() && (request.getFoodName() == null || request.getFoodName().isBlank())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("validation_error", "Either foodName or an image must be provided."));
        }

        try {
            AnalyzeResponse response = foodAnalysisService.analyze(request);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(503)
                    .body(new ErrorResponse("service_unavailable", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(502)
                    .body(new ErrorResponse("analysis_failed", e.getMessage()));
        }
    }
}
