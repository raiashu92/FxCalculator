import com.akr.fx.service.ExchangeRateProvider;
import com.akr.fx.service.QueryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class QueryTest {

    @Test
    public void testQuerySuccess() {
        Assert.assertTrue(QueryService.verifyQuery("AUD 100.00 in USD"));
        Assert.assertTrue(QueryService.verifyQuery("JPY 100 in USD"));
        Assert.assertTrue(QueryService.verifyQuery("SGP 100.0 in INR"));
        Assert.assertTrue(QueryService.verifyQuery("sgp 100.0 in inr"));
    }

    @Test
    public void testQueryFail() {
        Assert.assertFalse(QueryService.verifyQuery("AUD 10 USD"));
        Assert.assertFalse(QueryService.verifyQuery("AUD USD"));
        Assert.assertFalse(QueryService.verifyQuery("AUD 100"));
        Assert.assertFalse(QueryService.verifyQuery("100 AUD to USD"));
    }

    @Test
    public void testQueryWithUnavailableCurrency() {
        ExchangeRateProvider exchr = ExchangeRateProvider.getInstance();
        List<String> output = QueryService.extractData("INR 100 in SGP", exchr);
        Assert.assertEquals("INR-SGP", output.get(0));
    }
}
