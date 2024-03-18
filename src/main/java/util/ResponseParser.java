package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.NbuCurrencyDto;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ResponseParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<NbuCurrencyDto> parseJsonToListCurrency(HttpResponse<String> response) {
        List<NbuCurrencyDto> nbuCurrencies = new ArrayList<>();
        try {
            nbuCurrencies = objectMapper.readValue(response.body(), new TypeReference<ArrayList<NbuCurrencyDto>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return nbuCurrencies;
    }
}
