package de.simplestock.services.stock;

import de.simplestock.model.Stock;
import de.simplestock.util.Currency;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * The RESTResource, modeling a stock exchange.
 */
@Path("stock")
public class StockResource {
	private Stock stock = new Stock();
	
    /**
     * Gets the all currencies.
     *
     * @return the all currencies
     */
    @GET
    @Path("currencies")
	@Produces("text/json")
    public String getAllCurrencies() {
    	return stock.getAllCurrenciesJson();
    }
    
    /**
     * Gets the EUR-rate for a given currency and date.
     *
     * @param currency the currency
     * @param date the date
     * @return EUR-rate
     */
    @GET
    @Path("{currency}/{date}")
    @Consumes("text/json")
    @Produces("text/plain")
    public String getRateByDate(@PathParam("currency") String currency, 
    		@PathParam("date") String date) {
    	return stock.findRateByDate(Currency.findByShortcut(currency), date);
    }
    
    /**
     * Gets the all EUR-rates for a given date.
     *
     * @param date the date
     * @return all EUR-rates
     */
    @GET
    @Path("{date}")
    @Consumes("text/json")
    @Produces("text/json")
    public String getAllRates(@PathParam("date") String date) {
    	return stock.findAllRatesByDate(date);
    }
    
    /**
     * Returns the conversion by date (amount is sent via POST e.g. in Postman).
     *
     * @param currency the currency
     * @param date the date
     * @param amount the amount
     * @return the conversion by date
     */
    @POST
    @Path("convert/{currency}/{date}")
    @Consumes("text/json")
    @Produces("text/plain")
    public Response getConversionByDate(@PathParam("currency") String currency,
    		@PathParam("date") String date, Double amount) {
    	final String res = stock.convert(Currency.findByShortcut(currency), amount, date);
    	return Response.ok().entity(res).build();
    }
}
