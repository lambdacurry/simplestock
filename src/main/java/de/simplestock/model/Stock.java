package de.simplestock.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.simplestock.util.Currency;

/**
 * Model for a stock exchange.
 */
public class Stock {
	private static final Logger LOG = LoggerFactory.getLogger(Stock.class);
	private final DateFormat dateFormat;
	private final CsvReader csvReader;
	
	/**
	 * Instantiates a new stock.
	 */
	public Stock() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		csvReader = new CsvReader();
	}
	
    /**
     * Find EUR-rate for a given currency and date.
     *
     * @param currency the currency
     * @param date the date
     * @return the rate
     */
    public String findRateByDate(Currency currency, String date) {
    	if (!isValidDateFormat(date)) {
    		LOG.error("The date is not in correct format");
    		return null;
    	}
    	try {
    		if (!csvReader.getRatesData().containsKey(currency)) {
    			csvReader.addStockFile(currency);
    		}
			final String row = csvReader.getRatesData().get(currency).stream()
		          .filter(line -> line.contains(date))
		          .findFirst()
		          .orElse(null);
			return row == null || row.contains("Kein Wert vorhanden") 
						? "was not counted by BundesBank for this date"
						: row.split(";")[1];
		} catch (IOException e) {
			LOG.error("Exception while getting the data from Bundesbank", e);
	    	return null;
		}
    }
    
    /**
     * Find all EUR-rates for a given date.
     *
     * @param date the date
     * @return the rates in JSON-format
     */
    public String findAllRatesByDate(String date) {
    	Map<Currency, String> rates = new HashMap<>();
    	getAllCurrencies().forEach(currency -> {
    		final String rate = findRateByDate(currency, date);
    		if (rate != null && !rate.contains("was not counted")) {
    			rates.put(currency, rate);
    		}
    	});
		try {
			final String jsonRates = new ObjectMapper().writerWithDefaultPrettyPrinter()
					  .writeValueAsString(rates);
	    	return jsonRates;
		} catch (JsonProcessingException e) {
			LOG.error("Exception while serializing data to JSON", e);
			return null;
		}
    }
    
    /**
     * Convert the given amount of currency to EUR with the course of a given date.
     *
     * @param currency the currency
     * @param foreignAmount the amount of non-EUR currency
     * @param date the date
     * @return equal amount in EUR according to the course
     */
    public String convert(Currency currency, Double foreignAmount, String date) {
    	final String euroRateString = findRateByDate(currency, date);
    	if (euroRateString == null || euroRateString.contains("was not counted")) {
    		return "no euro rate is given for chosen currency and date";
    	}
    	final Double euroRateDouble = Double.parseDouble(euroRateString.replace(",", "."));
    	return Double.toString(foreignAmount/euroRateDouble);

    }
    
    /**
     * Gets the all existing currencies.
     *
     * @return currencies
     */
    public static List<Currency> getAllCurrencies() {
    	return new ArrayList<>(EnumSet.allOf(Currency.class));
    }
    
    /**
     * Gets the all currencies in JSON-format.
     *
     * @return currencies in JSON
     */
    public String getAllCurrenciesJson() {
    	try {
    		List<String> currencies = 
    				getAllCurrencies().stream()
    				                  .map(currency -> currency.getShortcut())
			                          .collect(Collectors.toList());
			String jsonCurrencies = new ObjectMapper().writerWithDefaultPrettyPrinter()
					  .writeValueAsString(currencies);
			return "All currencies, for which EUR-rate is presented:\n" + jsonCurrencies;
		} catch (JsonProcessingException e) {
			LOG.error("Exception while serializing data to JSON", e);
			return null;
		}
    }
    
    /**
     * Checks if the given date is in valid date format.
     *
     * @param dateForValidation the date for validation
     * @return if date is valid
     */
    public boolean isValidDateFormat(String dateForValidation) {
        try {
        	return dateForValidation.equals(dateFormat.format
        			(dateFormat.parse(dateForValidation)));
        } catch (ParseException e) {
        	LOG.error("Exception while checking the date format", e);
        	return false;
        }
    }
    
    /**
     * Flush the {@link CsvReader}.
     */
    public void flush() {
    	csvReader.getRatesData().clear();
    }
    
    /**
     * Gets the {@link CsvReader} (for testing purposes).
     * 
     * @return csvReader
     */
    public CsvReader getReader() {
    	return csvReader;
    }
}
