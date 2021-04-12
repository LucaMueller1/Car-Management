package carmanagement.cockpit.car.dto;

public class Position {

    private Long car_id;
    private Double Latitude;
    private Double Longitude;

    private Position(Long id, Double Latitude, Double Longitude){
        this.Latitude =Latitude;
        this.Longitude=Longitude;
        this.car_id=id;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public Long getCar_id() {
        return car_id;
    }
}
