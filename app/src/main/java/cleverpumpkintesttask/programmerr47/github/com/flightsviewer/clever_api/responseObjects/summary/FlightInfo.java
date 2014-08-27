package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.XmlUtils;

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
     * Creates {@link FlightInfo} object from its JSON Counterpart.
     *
     * @param parser given XML parser with stream inside and placed on this object
     * @return new instance of FlightInfo or null, if json is null
     */
    public static FlightInfo getFromXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, TAG)) {
            Builder builder = new Builder()
                    .setCarrier(parser.getAttributeValue(null, CARRIER_ATTRIBUTE))
                    .setEq(parser.getAttributeValue(null, EQ_ATTRIBUTE))
                    .setNumber(parser.getAttributeValue(null, NUMBER_ATTRIBUTE));

            try {
                while(parser.next() != XmlPullParser.END_TAG);
            } catch (XmlPullParserException ignored) {
                ignored.printStackTrace();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }

            return builder.build();
        } else {
            return null;
        }
    }
}
