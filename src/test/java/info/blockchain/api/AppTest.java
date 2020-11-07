package info.blockchain.api;

import info.blockchain.api.blockexplorer.BlockExplorerAsyncTest;
import info.blockchain.api.blockexplorer.BlockExplorerTest;
import info.blockchain.api.exchangeRates.ExchangeRatesAsyncTest;
import info.blockchain.api.exchangeRates.ExchangeRatesTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BlockExplorerTest.class,
        BlockExplorerAsyncTest.class,
        ExchangeRatesTest.class,
        ExchangeRatesAsyncTest.class
})
public class AppTest extends TestCase {
    public static final int waitTime  = 10 * 1000;
    public static final boolean wait = false;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest (String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite () {
        return new TestSuite(AppTest.class);
    }


}
