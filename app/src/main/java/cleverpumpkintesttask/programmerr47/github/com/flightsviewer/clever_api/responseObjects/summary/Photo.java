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
public class Photo implements Parcelable {
    public static final String TAG = "photo";
    //----------------------ATTRUBITES---------------------------//
    private static final String SRC_ATTRIBUTE = "src";

    public static Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[0];
        }
    };

    private String source;

    private Photo(Builder builder) {
        this.source = builder.source;
    }

    private Photo(Parcel in) {
        this.source = in.readString();
    }

    public String getSource() {
        return source;
    }

    @Override
    public int describeContents() {
        return 0;//TODO figure out how to act with this stuff
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(source);
    }

    public static class Builder {
        private String source;

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }

    /**
     * Creates {@link Photo} object from its JSON Counterpart.
     *
     * @param parser given XML parser with stream inside and placed on this object
     * @return new instance of Photo or null, if json is null
     */
    public static Photo getFromXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, TAG)) {
            Builder builder = new Builder();

            builder.setSource(parser.getAttributeValue(null, SRC_ATTRIBUTE));

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
