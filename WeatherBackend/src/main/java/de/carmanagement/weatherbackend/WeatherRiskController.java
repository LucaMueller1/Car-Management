package de.carmanagement.weatherbackend;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class WeatherRiskController {

    @RequestMapping(value = "/calculateWeatherRisk", method = RequestMethod.POST)
    public WeatherRisk calculateWeatherRisk(@RequestBody WeatherRiskDTO weatherriskreceived) {
        WeatherRisk returned = retrieveFromApi(weatherriskreceived);

        return returned;

    }
    @RequestMapping(value = "/calculateWeatherRiskFordays", method = RequestMethod.POST)
    public ArrayList<WeatherRisk> calculateWeatherRisk(@RequestBody WeatherRiskDTO weatherriskreceived , @RequestParam Integer numberOfDays) {
        return retriveForDaysFromApi(weatherriskreceived,numberOfDays);

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
            //System.out.println("WeatherInfoResponse: " + responseBody);
            JSONObject response = new JSONObject(responseBody);
            JSONArray consolidatedWeather = new JSONArray(response.getJSONArray("consolidated_weather"));

            LocalDate dt = new LocalDate();

            for (Object obj : consolidatedWeather){
                JSONObject currentobj = (JSONObject) obj;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date tocompare = formatter.parse(currentobj.getString("applicable_date"));
                if (Days.daysBetween(dt, new LocalDate(tocompare)).getDays() == 0 ){
                    System.out.println(currentobj + System.lineSeparator() + System.lineSeparator());
                    double min_temp= currentobj.getDouble("min_temp");

                    if (min_temp > 0 && min_temp < 10.0){
                        Double temp_score= (1/min_temp) * 0.5;
                        returned.increaseScore(temp_score);
                        System.out.println("Temperature score:" + temp_score);
                    }
                    if (min_temp <0) returned.increaseScore((1/min_temp) * 0.5 * -1);

                    if (currentobj.getString("weather_state_abbr").equals("hr")) returned.increaseScore(0.2);
                    if (currentobj.getString("weather_state_abbr").equals("hs") || currentobj.getString("weather_state_abbr").equals("sl")) returned.increaseScore(0.4);
                    System.out.println("Final score for date: " +  formatter.format(tocompare)  + " "+ returned.getScore());
                    returned.setMinTemp(currentobj.getDouble("min_temp"));
                    returned.setMaxTemp(currentobj.getDouble("max_temp"));
                    returned.setWind(currentobj.getDouble("wind_speed"));
                    returned.setDate(tocompare);
                }


            }
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return returned;
    }

    public ArrayList<WeatherRisk> retriveForDaysFromApi(WeatherRiskDTO weatherriskreceived, int dayScopeFromNow){
        ArrayList<WeatherRisk> returned =  new ArrayList<>();
        Integer nearest = retrieveNearestCity(weatherriskreceived.getLatitude(),weatherriskreceived.getLongitude());
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
            //System.out.println("WeatherInfoResponse: " + responseBody);
            JSONObject response = new JSONObject(responseBody);
            JSONArray consolidatedWeather = new JSONArray(response.getJSONArray("consolidated_weather"));

            LocalDate dt = new LocalDate();

            for (Object obj : consolidatedWeather){
                JSONObject currentobj = (JSONObject) obj;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date tocompare = formatter.parse(currentobj.getString("applicable_date"));
                if (Days.daysBetween(dt, new LocalDate(tocompare)).getDays() <= dayScopeFromNow ){
                    WeatherRisk toadd = new WeatherRisk(weatherriskreceived.getLatitude(), weatherriskreceived.getLongitude());
                    System.out.println(currentobj + System.lineSeparator() + System.lineSeparator());
                    double min_temp= currentobj.getDouble("min_temp");

                    if (min_temp > 0 && min_temp < 10.0){
                        Double temp_score= (1/min_temp) * 0.5;
                        toadd.increaseScore(temp_score);
                        System.out.println("Temperature score:" + temp_score);
                    }
                    if (min_temp <0) toadd.increaseScore((1/min_temp) * 0.5 * -1);
                    if (currentobj.getString("weather_state_abbr").equals("hr")) toadd.increaseScore(0.2);
                    if (currentobj.getString("weather_state_abbr").equals("hs") || currentobj.getString("weather_state_abbr").equals("sl")) toadd.increaseScore(0.4);
                    System.out.println("Final score for date: " +  formatter.format(tocompare)  + " "+ toadd.getScore());
                    toadd.setMinTemp(currentobj.getDouble("min_temp"));
                    toadd.setMaxTemp(currentobj.getDouble("max_temp"));
                    toadd.setWind(currentobj.getDouble("wind_speed"));
                    toadd.setDate(tocompare);

                    returned.add(toadd);
                }


            }
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
           // System.out.println(httpresponse.getStatusLine());
            String responseBody = EntityUtils.toString(httpresponse.getEntity(), StandardCharsets.UTF_8);
           // System.out.println("Response body: " + responseBody);
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
