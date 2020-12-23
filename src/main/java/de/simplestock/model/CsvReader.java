package de.simplestock.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simplestock.util.Currency;

/**
 * Get and read CSV-data.
 */
public class CsvReader {
    private static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);
	private Map<Currency, List<String>> ratesData = new HashMap<>();
	
    /**
     * Gets the rates data.
     *
     * @return the rates data
     */
    public Map <Currency, List<String>> getRatesData() {
    	return ratesData;
    }
    
    /**
     * Adds the stock file from the Bundesbank to the bufferMap.
     *
     * @param currency the currency
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void addStockFile(Currency currency) throws IOException {
        HttpURLConnection connection = 
        		(HttpURLConnection) createCsvUrl(currency).openConnection();
        if (connection.getResponseCode() == 200) {
            try (InputStreamReader streamReader = 
            		new InputStreamReader(connection.getInputStream())) {
                BufferedReader bReader = new BufferedReader(streamReader);
    			if (bReader.readLine() == null) {
    				LOG.error("The file for the {} is empty", currency.getShortcut());
    				throw new IOException();
    			}
    			ratesData.put(currency, bReader.lines().collect(Collectors.toList()));
            }
        } else {
        	LOG.error("CSV-data for the {} is not available", currency.getShortcut());
        	throw new IOException();
        }
    }
    
    /**
     * Creates the proper url for getting the data from Bundesbank.
     *
     * @param currency the currency
     * @return the url
     * @throws MalformedURLException the malformed URL exception
     */
    public URL createCsvUrl(Currency currency) throws MalformedURLException {
        return new URL("https://www.bundesbank.de/statistic-rmi/StatisticDownload?tsId=BBEX3.D."
          + currency.getShortcut()
          + ".EUR.BB.AC.000&its_csvFormat=de&its_fileFormat=csv&mode=its");
    }
}
