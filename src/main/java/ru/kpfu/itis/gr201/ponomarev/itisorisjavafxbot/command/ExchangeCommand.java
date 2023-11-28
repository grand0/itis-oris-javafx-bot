package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import org.json.JSONException;
import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClient;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.client.HttpClientImpl;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.model.ExchangeRate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ExchangeCommand extends AbstractCommand {

    private static final HttpClient HTTP;
    private static final String API_KEY;
    private static final String DEFAULT_CURRENCY;

    static {
        HTTP = new HttpClientImpl();
        API_KEY = "19ee0ebf14893805de96f9c87f2a3048";
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
        params.put("access_key", API_KEY);
        params.put("source", baseCurrency);
        params.put("currencies", currency);
        params.put("format", "1");
        try {
            LocalDate today = LocalDate.now();
            params.put("start_date", today.minusDays(6));
            params.put("end_date", today);
            String response = HTTP.get("http://api.currencylayer.com/timeframe", params);
            JSONObject json = new JSONObject(response);
            JSONObject quotes = json.getJSONObject("quotes");
            Map<LocalDate, ExchangeRate> map = new HashMap<>();
            for (String str : quotes.keySet()) {
                LocalDate date = LocalDate.parse(str);
                double value = quotes.getJSONObject(str).getDouble(baseCurrency + currency);
                map.put(date, new ExchangeRate(baseCurrency, currency, value));
            }

            ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, currency, quotes.getJSONObject(today.toString()).getDouble(baseCurrency + currency));

            return exchangeRate + "\n" + plot(map);
        } catch (IOException e) {
            throw new CommandExecutionException("Couldn't connect to API. Exception: " + e);
        } catch (JSONException e) {
            throw new CommandExecutionException("Couldn't parse an answer from API. Exception: " + e);
        }
    }

    private String plot(Map<LocalDate, ExchangeRate> map) {
        final int PLOT_WIDTH = 56;
        final int PLOT_HEIGHT = 10;

        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        for (ExchangeRate e : map.values()) {
            min = Math.min(min, e.getValue());
            max = Math.max(max, e.getValue());
        }
        double delta = max - min;
        double valPerSymbol = delta / PLOT_HEIGHT;

        List<Integer> mainCols = new ArrayList<>();
        double finalMin = min;
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            ExchangeRate exchangeRate = e.getValue();
            mainCols.add((int) ((exchangeRate.getValue() - finalMin) / valPerSymbol));
        });

        int colsPerMainCol = PLOT_WIDTH / mainCols.size();
        int[] cols = new int[colsPerMainCol * (mainCols.size() - 1)];
        for (int i = 0; i < cols.length - 1; i++) {
            if (i % colsPerMainCol == 0) {
                cols[i] = mainCols.get(i / colsPerMainCol);
            } else {
                double frac = (double) (i % colsPerMainCol) / colsPerMainCol;
                cols[i] = (int) interpolate(mainCols.get(i / colsPerMainCol), mainCols.get(i / colsPerMainCol + 1), frac);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < PLOT_HEIGHT; y++) {
            for (int x = 0; x < cols.length; x++) {
                int v = PLOT_HEIGHT - y;
                if (cols[x] >= v) {
                    cols[x]--;
                    sb.append("#");
                } else {
                    sb.append(" ");
                }
            }
            if (y == 0) sb.append(" " + max);
            else if (y == PLOT_HEIGHT - 1) sb.append(" " + min);
            sb.append("\n");
        }

        LocalDate minDate = map.keySet().stream().min(LocalDate::compareTo).get();
        LocalDate maxDate = map.keySet().stream().max(LocalDate::compareTo).get();

        sb.append(minDate);
        for (int i = 0; i < cols.length - minDate.toString().length() - maxDate.toString().length() - 1; i++) {
            sb.append(" ");
        }
        sb.append(maxDate);

        return sb.toString();
    }

    private double interpolate(double start, double end, double frac) {
        return start + (end - start) * frac;
    }
}
