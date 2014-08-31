package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import android.content.Context;

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
     * @param context current context
     * @param price double representation of price
     * @return specific string representation
     */
    public static String convertPriceToString(Context context, double price) {
        Locale locale;
        if (context != null) {
            locale = context.getResources().getConfiguration().locale;
        } else {
            locale = Locale.getDefault();
        }

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(locale);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        return formatter.format(price);
    }
}
