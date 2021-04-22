package carmanagement.cockpit.dealer.dto;

public class RentRequest {
        private Long car_id;
        private Double latitude;
        private Double longitude;

        public RentRequest(Long car_id, Double latitude, Double longitude) {
                this.car_id = car_id;
                this.latitude = latitude;
                this.longitude = longitude;
        }

        public void setCar_id(Long car_id) {
                this.car_id = car_id;
        }

        public Long getCar_id() {
                return car_id;
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
}
