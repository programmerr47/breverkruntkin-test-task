package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.sorts.comparators;

import java.util.Comparator;

import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-31
 */
public class FlightDurationAscendingComparator  implements Comparator<TripSummary>{

    @Override
    public int compare(TripSummary lhs, TripSummary rhs) {
        String leftDuration = lhs.getDuration();
        String rightDuration = rhs.getDuration();

        if ((leftDuration == null) && ( rightDuration == null)) {
            return 0;
        } else if (leftDuration == null) {
            return -1;
        } else if (rightDuration == null) {
            return 1;
        } else {
            return leftDuration.compareToIgnoreCase(rightDuration);
        }
    }
}
