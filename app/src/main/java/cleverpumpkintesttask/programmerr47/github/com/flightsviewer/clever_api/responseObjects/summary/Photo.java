package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class Photo {
    public static final String TAG = "photo";
    //----------------------ATTRUBITES---------------------------//
    private static final String SRC_ATTRIBUTE = "src";

    private String source;

    private Photo(Builder builder) {
        this.source = builder.source;
    }

    public String getSource() {
        return source;
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
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of Photo or null, if json is null
     */
    public static Photo getFromXml(XmlPullParser parser) {
        //TODO
        return null;
    }
}
