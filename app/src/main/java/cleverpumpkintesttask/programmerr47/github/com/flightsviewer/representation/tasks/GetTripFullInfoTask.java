package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks;

import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.APIMethods;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.Trip;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

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
