import com.akr.fx.Money;
import com.akr.fx.exception.ForexException;
import com.akr.fx.service.CrossVia;
import com.akr.fx.service.ExchangeRateProvider;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FxCrossTest {
    ExchangeRateProvider exchr = ExchangeRateProvider.getInstance();
    CrossVia crossVia = new CrossVia();
    @Rule
    public ExpectedException crossException = ExpectedException.none();

    @Test
    public void testCross() {
        Money target = crossVia.cross("AUD", "DKK", 100);
        Assert.assertEquals("DKK 505.76", target.toString());
        Money target2 = crossVia.cross("JPY", "USD", 100);
        Assert.assertEquals("USD 0.83", target2.toString());
    }

    @Test
    public void testReverse() {
        Money target = crossVia.cross("AUD", "DKK", 100);
        Assert.assertEquals("DKK 505.76", target.toString());
        Money reverse = crossVia.cross("DKK", "AUD", 100);
        Assert.assertEquals("AUD 19.77", reverse.toString());
    }

    @Test
    public void testSameCurrency() {
        Money sameCash = crossVia.cross("USD", "USD", 100);
        Assert.assertEquals("USD 100.00", sameCash.toString());
    }

    @Test
    public void testCrossWithUnavailableCurrency() {
        crossException.expect(ForexException.class);
        crossException.expectMessage("Forex rate not available");
        Money target = crossVia.cross("INR", "SGP", 1000);
    }
}
