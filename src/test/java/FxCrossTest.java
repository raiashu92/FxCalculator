import com.akr.fx.Money;
import com.akr.fx.exception.ForexException;
import com.akr.fx.service.CrossVia;
import com.akr.fx.service.ExchangeRateProvider;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FxCrossTest {
    
    @Rule
    public ExpectedException crossException = ExpectedException.none();

    @Test
    public void testCross() {
        Money source1 = Money.cash(100, "AUD");
        Money target = CrossVia.cross(source1, "DKK");
        Assert.assertEquals("DKK 505.76", target.toString());
        Money source2 = Money.cash(100, "JPY");
        Money target2 = CrossVia.cross(source2, "USD");
        Assert.assertEquals("USD 0.83", target2.toString());
    }

    @Test
    public void testReverse() {
        Money source1 = Money.cash(100, "AUD");
        Money target = CrossVia.cross(source1, "DKK");
        Assert.assertEquals("DKK 505.76", target.toString());
        Money source2 = Money.cash(100, "DKK");
        Money reverse = CrossVia.cross(source2, "AUD");
        Assert.assertEquals("AUD 19.77", reverse.toString());
    }

    @Test
    public void testSameCurrency() {
        Money source = Money.cash(100, "USD");
        Money sameCash = CrossVia.cross(source, "USD");
        Assert.assertEquals("USD 100.00", sameCash.toString());
    }

}
