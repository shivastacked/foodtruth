package com.foodtruth.backend.service;
import com.foodtruth.backend.exception.AnalysisNotFoundException;
import com.foodtruth.backend.exception.FoodProductNotFoundException;
import com.foodtruth.backend.exception.IngredientAlreadyExistsException;
import com.foodtruth.backend.entity.Analysis;
import com.foodtruth.backend.entity.FoodProduct;
import com.foodtruth.backend.entity.ProductIngredient;
import com.foodtruth.backend.repository.AnalysisRepository;
import com.foodtruth.backend.repository.FoodProductRepository;
import com.foodtruth.backend.repository.IngredientRepository;
import com.foodtruth.backend.repository.ProductIngredientRepository;

import org.springframework.stereotype.Service;

import com.foodtruth.backend.dto.AnalysisRequest;
import com.foodtruth.backend.dto.AnalysisResponse;
import com.foodtruth.backend.dto.IngredientRequest;
import com.foodtruth.backend.dto.IngredientResponse;
import com.foodtruth.backend.entity.Ingredient;
import com.foodtruth.backend.dto.ProductRequest;
import com.foodtruth.backend.dto.ProductResponse;
import java.util.List;


@Service
public class FoodProductService {

        private final IngredientRepository ingredientRepository;
        private final ProductIngredientRepository productIngredientRepository;
        private final AnalysisRepository analysisRepository;

    private final FoodProductRepository foodProductRepository;

   public FoodProductService(
        FoodProductRepository foodProductRepository,
        IngredientRepository ingredientRepository,
        ProductIngredientRepository productIngredientRepository,AnalysisRepository analysisRepository) {

    this.foodProductRepository = foodProductRepository;
    this.ingredientRepository = ingredientRepository;
    this.productIngredientRepository = productIngredientRepository;
    this.analysisRepository = analysisRepository;
}

    public ProductResponse createProduct(ProductRequest request) {

    FoodProduct product = new FoodProduct(
            request.getName(),
            request.getBrand()
    );

    FoodProduct savedProduct = foodProductRepository.save(product);

    return new ProductResponse(
            savedProduct.getId(),
            savedProduct.getName(),
            savedProduct.getBrand()
    );
}
   public ProductResponse getProductById(Long id) {

    FoodProduct product = foodProductRepository.findById(id)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + id + " not found"
            ));

    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getBrand()
    );
}
public List<ProductResponse> getAllProducts() {

    List<FoodProduct> products = foodProductRepository.findAll();

    return products.stream()
            .map(product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getBrand()
            ))
            .toList();
}
public ProductResponse updateProduct(Long id, ProductRequest request) {

    FoodProduct product = foodProductRepository.findById(id)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + id + " not found"
            ));

    product.setName(request.getName());
    product.setBrand(request.getBrand());

    FoodProduct updatedProduct = foodProductRepository.save(product);

    return new ProductResponse(
            updatedProduct.getId(),
            updatedProduct.getName(),
            updatedProduct.getBrand()
    );
}
public void deleteProduct(Long id) {

    FoodProduct product = foodProductRepository.findById(id)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + id + " not found"
            ));

    foodProductRepository.delete(product);
}
    public void addIngredient(Long productId, IngredientRequest request) {

    FoodProduct product = foodProductRepository.findById(productId)  //productId is the id of the product to which we want to add an ingredient
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + productId + " not found"
            ));

    Ingredient ingredient = ingredientRepository.findByName(request.getName())
            .orElseGet(() -> ingredientRepository.save(
                    new Ingredient(request.getName())
            ));

boolean alreadyExists = productIngredientRepository.existsByProductIdAndIngredientId(productId,ingredient.getId()
        );

if (alreadyExists) {
    throw new IngredientAlreadyExistsException(
        "Ingredient is already associated with this product"); //already exists, throw exception
}

    ProductIngredient productIngredient =
            new ProductIngredient(product, ingredient);

    productIngredientRepository.save(productIngredient);
}

public List<IngredientResponse> getIngredients(Long productId) {

    foodProductRepository.findById(productId)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + productId + " not found"
            ));

    List<ProductIngredient> productIngredients =
            productIngredientRepository.findByProductId(productId);

    return productIngredients.stream()
            .map(productIngredient -> {
                Ingredient ingredient = productIngredient.getIngredient();

                return new IngredientResponse(
                        ingredient.getId(),
                        ingredient.getName()
                );
            })
            .toList();
}
public AnalysisResponse createAnalysis(
        Long productId,
        AnalysisRequest request) {

    FoodProduct product = foodProductRepository.findById(productId)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + productId + " not found"
            ));

    Analysis analysis = new Analysis(
            product,
            request.getHealthScore(),
            request.getScoreColor(),
            request.getVerdict(),
            request.getSummary(),
            request.getPersonalizedInsight()
    );

    if (request.getScoreFactors() != null) {
        analysis.setScoreFactors(request.getScoreFactors());
    }

    Analysis savedAnalysis = analysisRepository.save(analysis);

    return toAnalysisResponse(savedAnalysis);
}
private AnalysisResponse toAnalysisResponse(Analysis analysis) {

    return new AnalysisResponse(
            analysis.getId(),
            analysis.getProduct().getId(),
            analysis.getHealthScore(),
            analysis.getScoreColor(),
            analysis.getVerdict(),
            analysis.getSummary(),
            analysis.getPersonalizedInsight(),
            analysis.getCreatedAt(),
            analysis.getScoreFactors()
    );
}
public List<AnalysisResponse> getAnalyses(Long productId) {

    foodProductRepository.findById(productId)
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product with ID " + productId + " not found"
            ));

    return analysisRepository.findByProductId(productId)
            .stream()
            .map(this::toAnalysisResponse)
            .toList();
}
public AnalysisResponse getAnalysisById(Long analysisId) {

    Analysis analysis = analysisRepository.findById(analysisId)
            .orElseThrow(() -> new AnalysisNotFoundException(
                    "Analysis with ID " + analysisId + " not found"
            ));

    return toAnalysisResponse(analysis);
}
}
