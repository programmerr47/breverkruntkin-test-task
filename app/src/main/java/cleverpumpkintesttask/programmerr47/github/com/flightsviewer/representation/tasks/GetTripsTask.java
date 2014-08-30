package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks;

import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.APIMethods;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

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
