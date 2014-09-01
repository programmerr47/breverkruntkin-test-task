package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.tasks;

import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.APIMethods;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.Trip;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class GetTripFullInfoTask extends AsyncTaskWithListener<TripSummary, Void, Trip> {

    @Override
    protected Trip doInBackground(TripSummary... params) {
        if (params.length > 0) {
            TripSummary summary = params[0];
            APIMethods methods = new APIMethods();
            return methods.getFullTripInfo(summary);
        } else {
            return null;
        }
    }
}
