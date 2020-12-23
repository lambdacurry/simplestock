package de.simplestock.services.blueprint;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import de.simplestock.model.CsvReader;
import de.simplestock.model.Stock;
import de.simplestock.services.stock.SimpleJettyServer;
import de.simplestock.services.stock.StockResource;

/**
 * Unit-test for {@link StockResource} (not finished).
 */
public class BlueprintTest {
	
	@InjectMocks
	StockResource resource = new StockResource();
	
	@Mock
	Stock stock;
	
	@Mock
	CsvReader reader;
	
	private static SimpleJettyServer server = new SimpleJettyServer();
	private static String API_URL = "http://localhost:8090/stock/";
	private static CloseableHttpClient client;
	
	@BeforeEach
	public void setUp() {
		server.run();
		client = HttpClients.createDefault();
	}
	
	@Test
	public void TestAllActiveUrl() throws ClientProtocolException, IOException {
		// stock/currencies
		assertEquals(resource.getAllCurrencies(), getResponseData("currencies"));
		// stock/{currency}
		// rate EUR-RUB for 21-10-2020
		final String correctRate = "91,4095";
		final String emptyJSON = "{ }";
		assertEquals(correctRate, getResponseData("RUB/2020-10-21"));
		assertEquals("was not counted by BundesBank for this date", 
				getResponseData("RUB/2020-10-24"));
		assertEquals(emptyJSON, getResponseData("RUB"));
	}
	
	@AfterAll
	public static void cleanUp() throws IOException {
		server.stop();
		client.close();
		assertDoesNotThrow(() -> client.close());
	}
	
	/**
	 * Gets the data from response after the GET-request.
	 *
	 * @param url the part of checked url
	 * @return the response data
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getResponseData(String url) 
			throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(API_URL + url);
	    HttpResponse response = HttpClientBuilder.create().build().execute(request);
	    String responseData = new BasicResponseHandler().handleResponse(response);
		return responseData;
	}
}
