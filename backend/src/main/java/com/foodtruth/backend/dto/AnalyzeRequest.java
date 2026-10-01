package com.foodtruth.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Request body for POST /api/analyze.
 * Either foodName (text analysis) or imageData (photo analysis) must be present.
 */
public class AnalyzeRequest {

    @NotBlank(message = "foodName is required when no image is provided")
    @Size(max = 200, message = "foodName must be 200 characters or fewer")
    private String foodName;

    @Valid
    private HealthProfile profile;

    private String imageBase64;

    private String imageMediaType;

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public HealthProfile getProfile() { return profile; }
    public void setProfile(HealthProfile profile) { this.profile = profile; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public String getImageMediaType() { return imageMediaType; }
    public void setImageMediaType(String imageMediaType) { this.imageMediaType = imageMediaType; }

    public boolean hasImage() {
        return imageBase64 != null && !imageBase64.isBlank();
    }

    /**
     * Optional health profile sent with the analysis request so the AI can personalize results.
     */
    public static class HealthProfile {
        private String age;
        private String gender;
        private String weight;
        private String goal;
        private List<String> allergies;

        public String getAge() { return age; }
        public void setAge(String age) { this.age = age; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public String getWeight() { return weight; }
        public void setWeight(String weight) { this.weight = weight; }

        public String getGoal() { return goal; }
        public void setGoal(String goal) { this.goal = goal; }

        public List<String> getAllergies() { return allergies; }
        public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    }
}
