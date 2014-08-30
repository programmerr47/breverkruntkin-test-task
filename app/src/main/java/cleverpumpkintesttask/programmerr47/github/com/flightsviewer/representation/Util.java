package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class Util {

    /**
     * Coverts price value (double value) to specified String value.
     * <strong>For example: </strong> 2315346.00 converts to 2 315 346.00
     *
     * @param price double representation of price
     * @return specific string representation
     */
    public static String convertPriceToString(double price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        return formatter.format(price);
    }
}
