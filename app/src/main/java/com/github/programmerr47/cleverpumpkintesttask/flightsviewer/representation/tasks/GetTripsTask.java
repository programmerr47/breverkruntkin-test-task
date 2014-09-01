package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.tasks;

import java.util.List;

import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.APIMethods;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class GetTripsTask extends AsyncTaskWithListener<Void, Void, List<TripSummary>> {

    @Override
    protected List<TripSummary> doInBackground(Void... params) {
        APIMethods methods = new APIMethods();
        return methods.getAllTrips();
    }
}
