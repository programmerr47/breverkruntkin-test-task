package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators;

import java.util.Comparator;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-31
 */
public class FlightPriceDescendingComparator extends FlightPriceAscendingComparator implements Comparator<TripSummary> {

    @Override
    public int compare(TripSummary lhs, TripSummary rhs) {
        return -1 * super.compare(lhs, rhs);
    }
}
