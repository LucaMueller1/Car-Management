package carmanagement.cockpit.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUser {
    private Double latitude;
    private Double longitude;
    private String name;
    private String address;

    public NewUser(@JsonProperty("latitude") Double latitude,
                   @JsonProperty("longitude") Double longitude,
                   @JsonProperty("name") String name,
                   @JsonProperty("address") String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
