package de.carmanagement.weatherbackend;

public class WeatherRisk {

    Double latitude;
    Double longitude;
    Integer score;
    Integer wind;
    Double temp;
    Double rainProbability;

    public WeatherRisk(Double latitude, Double longitude, Integer score, Integer wind, Double temp, Double rainProbability) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.wind = wind;
        this.temp = temp;
        this.rainProbability = rainProbability;
    }

    public WeatherRisk(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(Double rainProbability) {
        this.rainProbability = rainProbability;
    }
}
