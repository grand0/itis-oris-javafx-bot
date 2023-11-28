package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import org.json.JSONException;
import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClient;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClientImpl;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.model.Weather;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WeatherCommand extends AbstractCommand {

    private static final HttpClient HTTP;
    private static final String API_KEY;

    static {
        HTTP = new HttpClientImpl();
        API_KEY = "bb33039dd12d1198e828074c5db9610e";
    }

    @Override
    public String execute() throws CommandExecutionException {
        if (args.length < 1) {
            throw new CommandExecutionException("Usage: " + CommandType.WEATHER.getUsage());
        }
        String city = String.join(" ", args);
        Map<String, Object> params = new HashMap<>();
        params.put("q", city);
        params.put("units", "metric");
        params.put("appid", API_KEY);
        String response;
        try {
            response = HTTP.get("https://api.openweathermap.org/data/2.5/weather", params);
        } catch (IOException e) {
            throw new CommandExecutionException("Couldn't connect to API. Exception: " + e);
        }
        try {
            JSONObject json = new JSONObject(response);
            String name = json.getString("name");
            double temp = json.getJSONObject("main").getDouble("temp");
            double humidity = json.getJSONObject("main").getDouble("humidity");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            Weather weather = new Weather(name, temp, humidity, description);
            return weather.toString();
        } catch (JSONException e) {
            throw new CommandExecutionException("Couldn't parse an answer from API. Exception: " + e);
        }
    }
}
