package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators;

import java.util.Comparator;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Price;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-31
 */
public class FlightPriceAscendingComparator implements Comparator<TripSummary>{

    @Override
    public int compare(TripSummary lhs, TripSummary rhs) {
        Price leftPrice = lhs.getPrice();
        Price rightPrice = rhs.getPrice();

        if ((leftPrice == null) && (rightPrice == null)) {
            return 0;
        } else if (leftPrice == null) {
            return -1;
        } else if (rightPrice == null) {
            return 1;
        } else {
            return Double.compare(leftPrice.getValue(), rightPrice.getValue());
        }
    }
}
