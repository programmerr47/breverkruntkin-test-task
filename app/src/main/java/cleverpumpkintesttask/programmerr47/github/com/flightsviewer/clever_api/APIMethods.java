package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.Trip;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.Constants;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.Util;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.XmlUtils;

/**
 * @author Michael Spisin
 * @since 2014-08-27
 */
public class APIMethods {
    private static final String GET_ALL_FLIGHTS = "flights0541.xml";
    private static final String GET_LIGHT_INFO_METHOD = "flights/";
    private static final String RESULT_TAG = "result";

    public List<TripSummary> getAllTrips() {
        String url = Constants.CLEVER_BASE_URL + GET_ALL_FLIGHTS;
        XmlPullParser parser = Util.sendGetRequest(url);
        return TripSummary.getListFromXml(parser, RESULT_TAG);
    }

    public Trip getFullTripInfo(TripSummary summary) {
        String url = Constants.CLEVER_BASE_URL + GET_LIGHT_INFO_METHOD + summary.getFlight().getNumber() + ".xml";
        XmlPullParser parser = Util.sendGetRequest(url);

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, RESULT_TAG)) {
            try {
                while (parser.next() != XmlPullParser.START_TAG);
                return Trip.getFromXml(parser);
            } catch (XmlPullParserException ignored) {
                ignored.printStackTrace();
                return null;
            } catch (IOException ignored) {
                ignored.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
