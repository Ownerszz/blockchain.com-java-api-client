/**
 *
 */
package info.blockchain.api.exchangeRates;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

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
 * @author Jay Bhosle
 *
 */
public class ExchangeRatesTest {

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
    public void testGetTickerResponseIsNotEmpty() throws Exception {

        Map<String, Currency> ticker = null;

        try {
            ticker =  exchange.getTicker();
        }catch (APIException e){
            Assume.assumeNoException(e);
        }
        assertFalse(ticker.isEmpty());

    }

    /**
     * Test that the Currency response object is constructed properly with valid values from the response.
     * @throws Exception
     */
    @Test
    public void testGetTickerResponseCurrencyValueIsNotNull() throws Exception {

        Map<String, Currency> ticker = null;

        try {
            ticker =  exchange.getTicker();
        }catch (APIException e){
            Assume.assumeNoException(e);
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
    public void testToBTCisOperational() throws Exception {
        BigDecimal value = null;

        try {
            value = exchange.toBTC("USD", new BigDecimal("8512.76"));
        }catch (APIException e){
            Assume.assumeNoException(e);
        }
        assertNotNull(value);

    }

}
