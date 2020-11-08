/**
 *
 */
package info.blockchain.api.exchangeRates;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import info.blockchain.api.AppTest;
import info.blockchain.api.blockexplorer.entity.XpubFull;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import info.blockchain.api.APIException;
import info.blockchain.api.exchangerates.Currency;
import info.blockchain.api.exchangerates.ExchangeRates;

/**
 * @author Ownerszz
 *
 */
public class ExchangeRatesAsyncTest {

    private ExchangeRates exchange;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        exchange = new ExchangeRates();

    }

    @After
    public void after() throws InterruptedException {
        if (AppTest.wait){
            Thread.sleep(AppTest.waitTime);
        }
    }

    /**
     * Test that GetTicker response is not empty.
     * @throws Exception
     */
    @Test
    public void getTickerResponseIsNotEmptyAsync() throws Throwable {
        Map<String, Currency> ticker = null;
        try {
            Future<Map<String, Currency>> tickerAsync = exchange.getTickerAsync();
            int waitCounter = 0;
            while (!tickerAsync.isDone()){
                waitCounter++;
            }
            assertTrue("The getTickerAsync must run Async", waitCounter > 0);
            ticker = tickerAsync.get();
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }



        assertFalse(ticker.isEmpty());
    }

    /**
     * Test that the Currency response object is constructed properly with valid values from the response.
     * @throws Exception
     */
    @Test
    public void testGetTickerResponseCurrencyValueIsNotNullAsync() throws Throwable {

        Map<String, Currency> ticker = null;
        try {
            Future<Map<String, Currency>> tickerAsync = exchange.getTickerAsync();
            int waitCounter = 0;
            while (!tickerAsync.isDone()){
                waitCounter++;
            }
            assertTrue("The getTickerAsync must run Async", waitCounter > 0);
            ticker = tickerAsync.get();
        }catch (ExecutionException e){
        if (e.getCause().getClass() == APIException.class){
            Assume.assumeNoException(e.getCause());
        }else {
            throw e.getCause();
        }
    }


    final String currencyKey1 = "USD";
        final String currencyKey2 = "GBP";

        if(ticker.containsKey(currencyKey1)) {

            assertTrue(ticker.get(currencyKey1).getBuy() != null &&
                    ticker.get(currencyKey1).getSell() != null &&
                    ticker.get(currencyKey1).getPrice15m() != null &&
                    ticker.get(currencyKey1).getSymbol() != null);

        }
        else
        {

            if(ticker.containsKey(currencyKey2)) {

                assertTrue(ticker.get(currencyKey2).getBuy() != null &&
                        ticker.get(currencyKey2).getSell() != null &&
                        ticker.get(currencyKey2).getPrice15m() != null &&
                        ticker.get(currencyKey2).getSymbol() != null);

            }
        }

    }

    /**
     * Test that the function ToBTC is operational.
     * @throws Exception
     */
    @Test
    public void testToBTCisOperationalAsync() throws Throwable {

        BigDecimal value = null;
        try {
            Future<BigDecimal> toBTCasync = exchange.toBTCAsync("USD", new BigDecimal("8512.76"));
            int waitCounter = 0;
            while (!toBTCasync.isDone()){
                waitCounter++;
            }
            assertTrue("The toBTCAsync must run Async", waitCounter > 0);
            value = toBTCasync.get();
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }



        assertNotNull(value);

    }

}
