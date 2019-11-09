import com.akr.fx.Money;
import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class MoneyTest {
    @Rule
    public ExpectedException zeroAndNegativeException = ExpectedException.none();
    @Rule
    public ExpectedException wrongConversionException = ExpectedException.none();

    @Test
    public void testCreateMoney() {
        Money tenDollar = Money.cash(10, "USD");
        Assert.assertNotNull(tenDollar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongCurrency() {
        Money wrongCcy = Money.cash(10, "TYU");
    }

    @Test(expected = ComparisonFailure.class)
    public void testToStringFail() {
        Money oneRupees = Money.cash(1, "INR");
        Assert.assertEquals("1 rupee", oneRupees.toString());
    }

    @Test
    public void testToStringSuccess() {
        Money hundredRinggit = Money.cash(100, "MYR");
        Money thousandJapaneseYen = Money.cash(1000, "JPY");
        Assert.assertEquals("MYR 100.00", hundredRinggit.toString().trim());
        Assert.assertEquals("JPY 1000", thousandJapaneseYen.toString().trim());
    }

    @Test
    public void testZeroAndNegativeMoney() {
        zeroAndNegativeException.expect(RuntimeException.class);
        zeroAndNegativeException.expectMessage("Money cannot be 0 or -ve");
        Money zero = Money.cash(0, "AUD");
        Money negative = Money.cash(-1, "CAD");
    }

    @Test
    public void testWrongConversionRate() {
        wrongConversionException.expect(RuntimeException.class);
        wrongConversionException.expectMessage("conversion error occurred");
        Money.cash(1, "USD").convert("INR", 0);
    }
}