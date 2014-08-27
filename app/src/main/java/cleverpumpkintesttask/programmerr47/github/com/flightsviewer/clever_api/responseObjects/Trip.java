package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects;

import org.xmlpull.v1.XmlPullParser;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Description;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightInfo;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightPoint;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Photo;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Price;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class Trip {
    public static final String TAG = "trip";
    //----------------------ATTRUBITES---------------------------//
    private static final String DURATION_ATTRIBUTE = "duration";
    //-------------------------TAGS------------------------------//
    private static final String TAKEOFF_TAG = "takeoff";
    private static final String LANDING_TAG = "landing";
    private static final String FLIGHT_TAG = "flight";
    private static final String PRICE_TAG = "price";
    private static final String DESCRIPTION_TAG = "description";
    private static final String PHOTO_TAG = "photo";

    private String duration;
    private FlightPoint takeoff;
    private FlightPoint landing;
    private FlightInfo flight;
    private Price price;
    private Description description;
    private Photo photo;

    private Trip(Builder builder) {
        this.duration = builder.duration;
        this.takeoff = builder.takeoff;
        this.landing = builder.landing;
        this.flight = builder.flight;
        this.price = builder.price;
        this.description = builder.description;
        this.photo = builder.photo;
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

    public Description getDescription() {
        return description;
    }

    public Photo getPhoto() {
        return photo;
    }

    public static class Builder {
        private String duration;
        private FlightPoint takeoff;
        private FlightPoint landing;
        private FlightInfo flight;
        private Price price;
        private Description description;
        private Photo photo;

        public Builder() {}

        public Builder(TripSummary summary) {
            this.duration = summary.getDuration();
            this.takeoff = summary.getTakeoff();
            this.landing = summary.getLanding();
            this.flight = summary.getFlight();
            this.price = summary.getPrice();
        }

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

        public Builder setDescription(Description description) {
            this.description = description;
            return this;
        }

        public Builder setPhoto(Photo photo) {
            this.photo = photo;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }

    /**
     * Creates {@link Trip} object from its JSON Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of Trip or null, if json is null
     */
    public static FlightPoint getFromXml(XmlPullParser parser) {
        //TODO
        return null;
    }
}