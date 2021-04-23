package carmanagement.cockpit.dealer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewDealer {
    private Double latitude;
    private Double longitude;
    private String name;

    public NewDealer(@JsonProperty("latitude") Double latitude,
                     @JsonProperty("longitude") Double longitude,
                     @JsonProperty("name") String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
