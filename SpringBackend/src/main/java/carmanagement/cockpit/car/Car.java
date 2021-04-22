package carmanagement.cockpit.car;

import carmanagement.cockpit.dealer.Dealer;
import carmanagement.cockpit.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dealer_id", nullable = false)
    @JsonIgnore
    private Dealer dealer;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(columnDefinition="integer", name = "user_id", nullable = true)
    @JsonIgnore
    private User user;

    @Column(name= "latitude")
    private Double Latitude;

    @Column(name= "longitude")
    private Double Longitude;

    @Column(name = "price")
    private Double price;

    public Car(Long id, String brand, Dealer dealer, Double latitude, Double longitude, Double price, User user) {
        this.id = id;
        this.brand = brand;
        this.dealer = dealer;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.price = price;
        this.user = user;
    }

    public Car() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public User getUser() {
        return user;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
