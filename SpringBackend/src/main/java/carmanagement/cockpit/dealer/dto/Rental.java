package carmanagement.cockpit.dealer.dto;

import carmanagement.cockpit.car.Car;

import java.util.Set;

public class Rental {

    private Car car;
    private Long dealer_id;
    private double price;

    public Rental(Car car, Long dealer_id, double price) {
        this.car = car;
        this.dealer_id = dealer_id;
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(Long dealer_id) {
        this.dealer_id = dealer_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
