package carmanagement.cockpit.car;

import carmanagement.cockpit.dealer.Dealer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnore
    private Dealer dealer;

    @Column(name= "locationX")
    private String locationX;

    @Column(name= "locationy")
    private String locationY;

    public Car(Long id, String brand, Dealer dealer, String locationX, String locationY) {
        this.id = id;
        this.brand = brand;
        this.dealer = dealer;
        this.locationX = locationX;
        this.locationY = locationY;
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

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }
}
