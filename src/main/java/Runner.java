import model.NbuCurrencyDto;
import org.modelmapper.ModelMapper;
import service.NbuService;
import util.ResponseParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpahw2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //task1
        List<NbuCurrencyDto> nbuCurrencies = receiveNbuExchangeList();

        nbuCurrencies.forEach(System.out::println);

        NbuService nbuService = new NbuService(entityManager, new ModelMapper());

        //task2
        nbuService.saveNbuCurrencyList(nbuCurrencies);

        //task3 a

        NbuCurrencyDto exchangeRateByDate  = nbuService.receiveExchangeRateByDate("2022-01-05");
        System.out.println("exchangeRateByDate = " + exchangeRateByDate);

        //task3 b
        double avg = nbuService.receiveAvgValueBetweenRangeOfDates("2022-01-01", "2022-01-18");

        //task3 c
        double max = nbuService.receiveMaxRateForPeriod("2022-01-01", "2022-01-18");
        double min = nbuService.receiveMinRateForPeriod("2022-01-01", "2022-01-18");
    }

    private static List<NbuCurrencyDto> receiveNbuExchangeList() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest build = HttpRequest.newBuilder(new URI("https://bank.gov.ua/NBU_Exchange/exchange_site?start=20220101&end=20230628&valcode=usd&sort=exchangedate&order=desc&json"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        return ResponseParser.parseJsonToListCurrency(response);
    }

}
