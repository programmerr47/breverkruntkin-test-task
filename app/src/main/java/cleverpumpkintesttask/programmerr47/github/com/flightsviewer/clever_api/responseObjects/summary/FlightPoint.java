package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Michae Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class FlightPoint {
    public static final String TAKEOF_TAG = "takeoff";
    public static final String LANDING_TAG = "landing";
    //----------------------ATTRUBITES---------------------------//
    private static final String DATE_ATTRIBUTE = "date";
    private static final String TIME_ATTRIBUTE = "time";
    private static final String CITY_ATTRIBUTE = "city";

    private String date;
    private String time;
    private String city;

    private FlightPoint(Builder builder) {
        this.date = builder.date;
        this.time = builder.time;
        this.city = builder.city;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCity() {
        return city;
    }

    public static class Builder {
        private String date;
        private String time;
        private String city;

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public FlightPoint build() {
            return new FlightPoint(this);
        }
    }

    /**
     * Creates {@link FlightPoint} object from its JSON Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of FlightPoint or null, if json is null
     */
    public static FlightPoint getFromXml(XmlPullParser parser) {
        //TODO
//        if (parser == null) {
//            return null;
//        }
//
//        return new Builder()
//                .setTime(parser.)
//                .setDate()
//                .setCity()
//                .build();
        return null;
    }
}
