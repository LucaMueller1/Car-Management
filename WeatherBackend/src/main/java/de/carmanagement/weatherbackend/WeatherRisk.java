package de.carmanagement.weatherbackend;

import java.util.Date;

public class WeatherRisk {

    Double latitude;
    Double longitude;
    Double score;
    Double wind;
    Double minTemp;
    Double maxTemp;
    Double rainProbability;
    Date date;

    public WeatherRisk(Double latitude, Double longitude, Double score, Double wind, Double minTemp, Double rainProbability) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.wind = wind;
        this.minTemp = minTemp;
        this.rainProbability = rainProbability;
    }

    public WeatherRisk(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = 0.0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
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

    public Double getScore() {
        return score;
    }
    public void increaseScore(Double increment){
        if (this.score >= 1.0) return;
        Double newscore = this.score + increment;
        if (newscore > 1.0) this.score = 1.0;
        else this.score = newscore;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(Double rainProbability) {
        this.rainProbability = rainProbability;
    }
}
