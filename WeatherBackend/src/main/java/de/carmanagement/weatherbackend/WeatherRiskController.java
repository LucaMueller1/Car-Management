package de.carmanagement.weatherbackend;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
public class WeatherRiskController {

    @RequestMapping(value = "/calculateWeatherRisk", method = RequestMethod.POST)
    public WeatherRisk calculateWeatherRisk(@RequestBody WeatherRiskDTO weatherriskreceived) {
        WeatherRisk returned = retrieveFromApi(weatherriskreceived);

        return returned;

    }

    public WeatherRisk retrieveFromApi(WeatherRiskDTO weatherriskreceived){
        WeatherRisk returned = new WeatherRisk(weatherriskreceived.getLatitude(), weatherriskreceived.getLongitude());
        Integer nearest = retrieveNearestCity(returned.getLatitude(),returned.getLongitude());
        URIBuilder builder = null;
        try {
            builder = new URIBuilder("https://www.metaweather.com/api/location/" + nearest + "/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(builder.build());
            HttpResponse httpresponse = httpclient.execute(get);
            System.out.println("Status of the request for weather infos: " + httpresponse.getStatusLine());
            String responseBody = EntityUtils.toString(httpresponse.getEntity(), StandardCharsets.UTF_8);
            System.out.println("WeatherInfoResponse: " + responseBody);
            JSONObject response = new JSONObject(responseBody);
            System.out.println(response);
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return returned;
    }

    public Integer retrieveNearestCity(Double latitude, Double longitude){
        URIBuilder builder = null;
        Integer woeidofnearestcity = 0;
        try {
            builder = new URIBuilder("https://www.metaweather.com/api/location/search/?=");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        builder.setParameter("lattlong", latitude+ "," + longitude);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(builder.build());

            //Executing the Get request
            HttpResponse httpresponse = httpclient.execute(get);


            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            String responseBody = EntityUtils.toString(httpresponse.getEntity(), StandardCharsets.UTF_8);
            System.out.println("Response body: " + responseBody);
            JSONArray response = new JSONArray(responseBody);
            JSONObject nearestcity= (JSONObject) response.get(0);
            woeidofnearestcity = (Integer) nearestcity.get("woeid");

            System.out.println(woeidofnearestcity);
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
return woeidofnearestcity;
    }

}
