package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarRepository;
import carmanagement.cockpit.dealer.dto.RentRequest;
import carmanagement.cockpit.dealer.dto.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.util.List;

@Service
public class DealerService {
    private static final Logger log = LoggerFactory.getLogger(DealerService.class);
    private static final String WeatherServiceUrl = "http://e45a7e99-a45e-4ecb-8b43-ec17337b634a.ma.bw-cloud-instance.org:8099/swagger-ui/";

    @Autowired
    private DealerRepository repository;

    @Autowired
    private CarRepository carRepository;

    public Dealer saveDealer(Dealer dealer) {
        return repository.save(dealer);
    }

    public List<Dealer> findAll() {
        return repository.findAll();
    }

    public Rental rentCar(RentRequest rentRequest){
        Car car = carRepository.getCarById(rentRequest.getCar_id()).get();
        double price = car.getPrice();
//        call daniels weather service
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(WeatherServiceUrl);
//        method.getParams().setParameter();
        try {
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Fail -> " + method.getStatusCode());
            }
            String response = new String(method.getResponseBody());
            log.info(response);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Rental(car, car.getDealer().getId(), price);
    }
}
