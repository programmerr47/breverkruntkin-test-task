package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api;

import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.Trip;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spisin
 * @since 2014-08-27
 */
public class APIMethods {
    private static final String GET_ALL_FLIGHTS = "flights0541.xml";
    private static final String GET_LIGHT_INFO_METHOD = "flights/";

    public List<TripSummary> getAllTrips() {
        String url = Constants.CLEVER_BASE_URL + GET_ALL_FLIGHTS;

        //TODO
        return null;
    }

    public Trip getFullTripInfo(TripSummary summary) {
        String url = Constants.CLEVER_BASE_URL + GET_LIGHT_INFO_METHOD + summary.getFlight().getNumber() + ".xml";

        //TODO
        return null;
    }
}
