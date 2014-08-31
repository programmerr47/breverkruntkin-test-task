package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import android.os.Parcel;
import android.os.Parcelable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.XmlUtils;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class Description implements Parcelable {
    public static final String TAG = "description";

    public static Creator<Description> CREATOR = new Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel source) {
            return new Description(source);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[0];
        }
    };

    private String value;

    private Description(Builder builder) {
        this.value = builder.value;
    }
    private Description(Parcel in) {
        this.value = in.readString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;//TODO figure out how to act with this stuff
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(value);
    }

    public static class Builder {
        private String value;

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Description build() {
            return new Description(this);
        }
    }

    /**
     * Creates {@link Description} object from its JSON Counterpart.
     *
     * @param parser given XML parser with stream inside and placed on this object
     * @return new instance of Description or null, if json is null
     */
    public static Description getFromXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, TAG)) {
            Builder builder = new Builder();

            String value = XmlUtils.getText(parser);

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
