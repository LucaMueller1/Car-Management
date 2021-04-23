package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarRepository;
import carmanagement.cockpit.dealer.dto.NewDealer;
import carmanagement.cockpit.dealer.dto.RentRequest;
import carmanagement.cockpit.dealer.dto.Rental;
import carmanagement.cockpit.user.UserRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.apache.commons.httpclient.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DealerService {
    private static final Logger log = LoggerFactory.getLogger(DealerService.class);
    private static final String WeatherServiceUrl = "http://e45a7e99-a45e-4ecb-8b43-ec17337b634a.ma.bw-cloud-instance.org:8099/calculateWeatherRisk";

    @Autowired
    private DealerRepository repository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public Dealer save(NewDealer newDealer) {
        Dealer dealer1 = new Dealer(null, newDealer.getName(),newDealer.getLatitude(), newDealer.getLongitude());
        return repository.save(dealer1);
    }

    public List<Dealer> findAll() {
        return repository.findAll();
    }

    public Rental priceCar(RentRequest rentRequest) {
        Car car = carRepository.getCarById(rentRequest.getCar_id()).get();
        double price = car.getPrice();
//        call daniels weather service
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(WeatherServiceUrl);
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("latitude", String.valueOf(rentRequest.getLatitude())));
        params.add(new BasicNameValuePair("longitude", String.valueOf(rentRequest.getLongitude())));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        double riskFactor;
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                log.info("rentCar: content = {}", response.getEntity().getContent());
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.info("ResponseBody: {}", responseBody);
                JSONObject obj = new JSONObject(responseBody);
                double score = obj.getDouble("score");
                log.info("rentCar: adding Risk {} to price factor {}", score, car.getPrice());
                price=price*(score+1)*0.75;
                log.info("rentCar: new Price -> {}", price);
            } else {
                log.info("rentCar: WeatherRisk Fail -> ResponseCode: {}", response.getStatusLine().getStatusCode());

            }
            log.info("rentCar: WeatherRisk response: {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return new Rental(car, car.getDealer().getId(), price);
    }

    public Long rentCar(RentRequest rentRequest){
        Rental rental = priceCar(rentRequest);
        Car car = rental.getCar();
        if (repository.findById(rental.getDealer_id()).isPresent()) car.setDealer(repository.findById(rental.getDealer_id()).get());
        car.setPrice(rental.getPrice());
        if (userRepository.findById(rentRequest.getUser_id()).isPresent()) car.setUser(userRepository.findById(rentRequest.getUser_id()).get());
        carRepository.save(car);
        log.info("Renting car {} with user {}", car.getId(), car.getUser().getId());
        return car.getId();
    }

    public Long returnCar(Long car_id){
        Car car = null;
        if (carRepository.findById(car_id).isPresent()) car = carRepository.findById(car_id).get();
        log.info("Returned Car {}", car_id);
        car.setUser(null);
        carRepository.save(car);
        return car_id;
    }
}
