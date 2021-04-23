package carmanagement.cockpit.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCar {
    private Double latitude;
    private Double longitude;
    private String brand;
    private Long dealer_id;
    private Double price;

    public NewCar(@JsonProperty("latitude") Double latitude,
                  @JsonProperty("longitude") Double longitude,
                  @JsonProperty("brand") String brand,
                  @JsonProperty("dealer_id") Long dealer_id,
                  @JsonProperty("price") Double price) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.brand = brand;
        this.dealer_id = dealer_id;
        this.price = price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(Long dealer_id) {
        this.dealer_id = dealer_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
