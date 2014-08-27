package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class FlightInfo {
    public static final String TAG = "flight";
    //----------------------ATTRUBITES---------------------------//
    private static final String CARRIER_ATTRIBUTE = "carrier";
    private static final String NUMBER_ATTRIBUTE = "number";
    private static final String EQ_ATTRIBUTE = "eq";

    private String carrier;
    private String number;
    private String eq;

    private FlightInfo(Builder builder) {
        this.carrier = builder.carrier;
        this.number = builder.number;
        this.eq = builder.eq;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getNumber() {
        return number;
    }

    public String getEq() {
        return eq;
    }

    public static class Builder {
        private String carrier;
        private String number;
        private String eq;

        public Builder setCarrier(String carrier) {
            this.carrier = carrier;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setEq(String eq) {
            this.eq = eq;
            return this;
        }

        public FlightInfo build() {
            return new FlightInfo(this);
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
        return null;
    }
}
