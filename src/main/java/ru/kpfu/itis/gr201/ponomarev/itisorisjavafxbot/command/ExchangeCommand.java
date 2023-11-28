package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import org.json.JSONException;
import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClient;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClientImpl;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.model.ExchangeRate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExchangeCommand extends AbstractCommand {

    private static final HttpClient HTTP;
    private static final String API_KEY;
    private static final String DEFAULT_CURRENCY;

    static {
        HTTP = new HttpClientImpl();
        API_KEY = "cur_live_XiX0TwbGLbUuQtvdkaqVCODc5uPLg54tYOSVrRyf";
        DEFAULT_CURRENCY = "RUB";
    }

    @Override
    public String execute() throws CommandExecutionException {
        if (args.length < 1 || args.length > 2) {
            throw new CommandExecutionException("Usage: " + CommandType.EXCHANGE.getUsage());
        }
        String baseCurrency = args[0].toUpperCase();
        String currency = DEFAULT_CURRENCY;
        if (args.length == 2) {
            currency = args[1].toUpperCase();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("base_currency", baseCurrency);
        params.put("currencies", currency);
        try {
            String response = HTTP.get("https://api.currencyapi.com/v3/latest", params);
            JSONObject json = new JSONObject(response);
            double value = json.getJSONObject("data").getJSONObject(currency).getDouble("value");
            ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, currency, value);
            return exchangeRate.toString();
        } catch (IOException e) {
            throw new CommandExecutionException("Couldn't connect to API. Exception: " + e);
        } catch (JSONException e) {
            throw new CommandExecutionException("Couldn't parse an answer from API. Exception: " + e);
        }
    }
}
