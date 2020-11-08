package info.blockchain.api.blockexplorer;


import info.blockchain.api.APIException;
import info.blockchain.api.AppTest;
import info.blockchain.api.blockexplorer.entity.*;
import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by Ownerszz on 06/11/2020.
 */
public class BlockExplorerAsyncTest {

    BlockExplorer client;

    @Before
    public void setUp () throws Throwable {
        client = new BlockExplorer();
    }

    @After
    public void after() throws InterruptedException {
        if (AppTest.wait){
            Thread.sleep(AppTest.waitTime);
        }
    }

    @Test
    public void getAddressAsync () throws Throwable {
        Address address = null;
        try {
            int waitCounter = 0;
            Future<Address> addressFuture = client.getAddressAsync("1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW", FilterType.All, 10, null);
            while (!addressFuture.isDone()){
                waitCounter++;
            }
            address = addressFuture.get();
            assertTrue("The getAddressAsync must run Async", waitCounter > 0);
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }

        assertEquals("1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW", address.getAddress());
        assertEquals("07feead7f9fb7d16a0251421ac9fa090169cc169",
                address.getHash160());
        assertEquals("Requires a 0 final balance",0, address.getFinalBalance());
        assertTrue("Requires more than 0 transactions", address.getTxCount() > 0);
        assertTrue("Requires more than 20000 satoshis received", address.getTotalReceived() > 2000);
        assertTrue("Requires more than 20000 satoshis sent", address.getTotalSent() > 2000);
        assertTrue("Requires less than or 10 transactions because of limit", address.getTransactions().size() <= 10);    }

    @Test
    public void getUnspentOutputsAsync () throws Throwable {
        String address1 = "1FrWWFJ95Jq7EDgpkeBwVLAtoJMPwmYS7T";
        String address2 = "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn";
        List<UnspentOutput> unspentOutputs = null;
        try {
            int waitCounter = 0;
            Future<List<UnspentOutput>> listFuture = client.getUnspentOutputsAsync(Arrays.asList(address1, address2), 6, 10);
            while (!listFuture.isDone()){
                waitCounter++;
            }
            unspentOutputs = listFuture.get();
            assertTrue("The getUnspentOutputsAsync must run Async", waitCounter > 0);
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }

        assertTrue(unspentOutputs != null && unspentOutputs.size() != 0);
        assertEquals("2e7ab41818ee0ab987d393d4c8bf5e436b6e8c15ef3535a2b3eac581e33c7472", unspentOutputs.get(0).getTransactionHash());
        assertEquals(20000, unspentOutputs.get(0).getValue());
    }

    @Test
    public void getBalanceAsync () throws Throwable {
        String address1 = "1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW";
        String address2 = "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn";
        Map<String, Balance> balances = null;
        try {
            List<String> list = Arrays.asList(address1, address2);
            int waitCounter = 0;
            Future<Map<String, Balance>> mapFuture = client.getBalanceAsync(list, FilterType.All);
            while (!mapFuture.isDone()){
                waitCounter++;
            }
            balances = mapFuture.get();
            assertTrue("The getBalanceAsync must run Async", waitCounter > 0);
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }


        assertEquals("Requires a 0 final balance",0, balances.get(address1).getFinalBalance());
        assertTrue("Requires more than 0 transactions", balances.get(address1).getTxCount() > 0);
        assertTrue("Requires more than 20000 satoshis received", balances.get(address1).getTotalReceived() > 2000);
        assertTrue("Requires a final balance greater than 0",balances.get(address2).getFinalBalance() > 0);
        assertEquals("Requires 0 transactions",0, balances.get(address2).getTxCount());
        assertEquals("Requires 0 satoshis received",0, balances.get(address2).getTotalReceived());
    }

    @Test
    public void getMultiAddressAsync () throws Throwable {
        String address1 = "1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW";
        String address2 = "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn";
        List<String> list = Arrays.asList(address1, address2);
        MultiAddress multiAddress = null;
        try {
            int waitCounter = 0;
            Future<MultiAddress> multiAddressFuture = client.getMultiAddressAsync(list, FilterType.All, null, null);
            while (!multiAddressFuture.isDone()){
                waitCounter++;
            }
            multiAddress = multiAddressFuture.get();
            assertTrue("The getMultiAddressAsync must run Async", waitCounter > 0);
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }


        //Addresses
        Optional<AddressSummary> addressSummary1 = multiAddress.getAddresses().stream().filter(e -> e.getAddress().equals(address1)).findFirst();
        assertTrue(addressSummary1.isPresent());
        assertEquals("1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW", addressSummary1.get().getAddress());
        assertTrue("Requires more than 0 transactions", addressSummary1.get().getTxCount() > 0);
        assertTrue("Requires more than 20000 satoshis received", addressSummary1.get().getTotalReceived() > 2000);
        assertTrue("Requires more than 20000 satoshis sent", addressSummary1.get().getTotalSent() > 2000);
        assertEquals("Requires a 0 final balance",0, addressSummary1.get().getFinalBalance());

        Optional<AddressSummary> addressSummary2 = multiAddress.getAddresses().stream().filter(e -> e.getAddress().equals(address2)).findFirst();
        assertTrue(addressSummary2.isPresent());
        assertEquals("xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn",
                addressSummary2.get().getAddress());
    }

    @Test
    public void getXpubAsync () throws Throwable {
        String address = "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn";
        XpubFull xpub = null;
        try {
            int waitCounter = 0;
            Future<XpubFull> xpubFullFuture = client.getXpubAsync(address, null, null, null);
            while (!xpubFullFuture.isDone()){
                waitCounter++;
            }
            xpub = xpubFullFuture.get();

            assertTrue("The getXpubAsync must run Async", waitCounter > 0);
        }catch (ExecutionException e){
            if (e.getCause().getClass() == APIException.class){
                Assume.assumeNoException(e.getCause());
            }else {
                throw e.getCause();
            }
        }



        assertEquals(xpub.getAddress(),
                "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn");
        assertEquals("Requires 0 transactions",0, xpub.getTxCount());

        assertEquals("Requires 0 satoshis received",0, xpub.getTotalReceived());

        assertEquals("Requires 0 satoshis sent",0, xpub.getTotalReceived());

        assertTrue("Requires a final balance greater than 0",xpub.getFinalBalance() > 0);


    }

}