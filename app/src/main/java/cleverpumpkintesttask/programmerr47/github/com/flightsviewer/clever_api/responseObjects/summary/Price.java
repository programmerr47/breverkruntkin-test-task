package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class Price {
    public static final String TAG = "price";

    private double value;

    private Price(Builder builder) {
        this.value = builder.value;
    }

    public double getValue() {
        return value;
    }

    public static class Builder {
        private double value;

        public Builder setValue(double value) {
            this.value = value;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }

    /**
     * Creates {@link Price} object from its JSON Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of Price or null, if json is null
     */
    public static Price getFromXml(XmlPullParser parser) {
        //TODO
        return null;
    }
}
