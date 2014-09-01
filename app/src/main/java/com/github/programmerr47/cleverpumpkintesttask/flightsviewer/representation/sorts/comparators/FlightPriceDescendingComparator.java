package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.sorts.comparators;

import java.util.Comparator;

import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.summary.TripSummary;

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
