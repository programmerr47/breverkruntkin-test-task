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
     * @param parser given XML parser with stream inside and placed on this object
     * @return new instance of Price or null, if json is null
     */
    public static Price getFromXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, TAG)) {
            Builder builder = new Builder();

            double value = Double.parseDouble(XmlUtils.getText(parser));

            try {
                parser.nextTag();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (XmlUtils.isCorrect(parser, XmlPullParser.END_TAG, null, TAG)) {
                builder.setValue(value);
            }

            return builder.build();
        } else {
            return null;
        }
    }
}
