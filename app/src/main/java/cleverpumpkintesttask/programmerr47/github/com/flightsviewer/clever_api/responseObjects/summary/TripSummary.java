package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class TripSummary {
    public static final String TAG = "trip";
    //----------------------ATTRUBITES---------------------------//
    private static final String DURATION_ATTRIBUTE = "duration";
    //-------------------------TAGS------------------------------//
    private static final String TAKEOFF_TAG = "takeoff";
    private static final String LANDING_TAG = "landing";
    private static final String FLIGHT_TAG = "flight";
    private static final String PRICE_TAG = "price";
    private static final String DESCRIPTION_TAG = "description";

    private String duration;
    private FlightPoint takeoff;
    private FlightPoint landing;
    private FlightInfo flight;
    private Price price;

    private TripSummary(Builder builder) {
        this.duration = builder.duration;
        this.takeoff = builder.takeoff;
        this.landing = builder.landing;
        this.flight = builder.flight;
        this.price = builder.price;
    }

    public String getDuration() {
        return duration;
    }

    public FlightPoint getTakeoff() {
        return takeoff;
    }

    public FlightPoint getLanding() {
        return landing;
    }

    public FlightInfo getFlight() {
        return flight;
    }

    public Price getPrice() {
        return price;
    }

    public static class Builder {
        private String duration;
        private FlightPoint takeoff;
        private FlightPoint landing;
        private FlightInfo flight;
        private Price price;

        public Builder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder setTakeoff(FlightPoint takeoff) {
            this.takeoff = takeoff;
            return this;
        }

        public Builder setLanding(FlightPoint landing) {
            this.landing = landing;
            return this;
        }

        public Builder setFlight(FlightInfo flight) {
            this.flight = flight;
            return this;
        }

        public Builder setPrice(Price price) {
            this.price = price;
            return this;
        }

        public TripSummary build() {
            return new TripSummary(this);
        }
    }

    /**
     * Creates {@link TripSummary} object from its JSON Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of TripSummary or null, if json is null
     */
    public static FlightPoint getFromXml(XmlPullParser parser) {
        //TODO
        return null;
    }
}
