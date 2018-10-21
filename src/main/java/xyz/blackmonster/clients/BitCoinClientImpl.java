package xyz.blackmonster.clients;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.core.util.IOUtils;
import org.apache.storm.shade.org.apache.http.HttpResponse;
import org.apache.storm.shade.org.apache.http.client.HttpClient;
import org.apache.storm.shade.org.apache.http.client.methods.HttpGet;
import org.apache.storm.shade.org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.blackmonster.models.BitCoinMarketValue;

/**
 * HTTP client that reads bitcoin value and returns the value in EUR
 */
public class BitCoinClientImpl implements BitCoinClient {

	private String url;

	public BitCoinClientImpl(String url) {
		this.url = url;
	}

	private BitCoinMarketValue retrieveValue() {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");
		try {
			HttpResponse response = client.execute(request);
			String json = IOUtils.toString(new InputStreamReader(response.getEntity().getContent()));
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, BitCoinMarketValue.class);
		} catch (IOException e) {
			System.out.println("Not able to retrieve value: " + e.getMessage());
			return null;
		}
	}

	public double readPriceEUR() {
		BitCoinMarketValue value = retrieveValue();
		if(value == null) {
			return -1;
		}

		return Double.parseDouble(value.getBitCoinPriceIndex().getEur().getRateFloat());
	}
}
