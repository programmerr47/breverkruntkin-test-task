package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary;

import android.os.Parcel;
import android.os.Parcelable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.util.XmlUtils;

/**
 * @author Michael Spitsin
 * @since 2014-08-27
 */
@SuppressWarnings("unused")
public class TripSummary implements Parcelable{
    public static final String TAG = "trip";
    //----------------------ATTRUBITES---------------------------//
    private static final String DURATION_ATTRIBUTE = "duration";
    //-------------------------TAGS------------------------------//
    private static final String TAKEOFF_TAG = "takeoff";
    private static final String LANDING_TAG = "landing";
    private static final String FLIGHT_TAG = "flight";
    private static final String PRICE_TAG = "price";
    private static final String DESCRIPTION_TAG = "description";

    public static Creator<TripSummary> CREATOR = new Creator<TripSummary>() {
        @Override
        public TripSummary createFromParcel(Parcel source) {
            return new TripSummary(source);
        }

        @Override
        public TripSummary[] newArray(int size) {
            return new TripSummary[0];
        }
    };

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

    private TripSummary(Parcel in) {
        this.duration = in.readString();
        this.takeoff = in.readParcelable(FlightPoint.class.getClassLoader());
        this.landing = in.readParcelable(FlightPoint.class.getClassLoader());
        this.flight = in.readParcelable(FlightInfo.class.getClassLoader());
        this.price = in.readParcelable(Price.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;//TODO figure out how to act with this stuff
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(duration);
        out.writeParcelable(takeoff, flags);
        out.writeParcelable(landing, flags);
        out.writeParcelable(flight, flags);
        out.writeParcelable(price, flags);
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
     * Creates {@link TripSummary} object from its XML Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of TripSummary or null, if xml is null
     */
    public static TripSummary getFromXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, TAG)) {
            Builder builder = new Builder()
                    .setDuration(parser.getAttributeValue(null, DURATION_ATTRIBUTE));

            try {
                while(parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (TAKEOFF_TAG.equals(parser.getName())) {
                        builder.setTakeoff(FlightPoint.getFromXml(parser, TAKEOFF_TAG));
                    } else if (LANDING_TAG.equals(parser.getName())) {
                        builder.setLanding(FlightPoint.getFromXml(parser, LANDING_TAG));
                    } else if (FLIGHT_TAG.equals(parser.getName())) {
                        builder.setFlight(FlightInfo.getFromXml(parser));
                    } else if (PRICE_TAG.equals(parser.getName())) {
                        builder.setPrice(Price.getFromXml(parser));
                    } else {
                        XmlUtils.skipTag(parser);
                    }
                }
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

    /**
     * Creates list of {@link TripSummary} objects from its XML Counterpart.
     *
     * @param parser - given XML parser with stream inside and placed on this object
     * @return new instance of {@code List<TripSummary>} or null, if xml is null
     */
    public static List<TripSummary> getListFromXml(XmlPullParser parser, String containerTag) {
        if (parser == null) {
            return null;
        }

        List<TripSummary> trips = new ArrayList<TripSummary>();

        if (XmlUtils.isCorrect(parser, XmlPullParser.START_TAG, null, containerTag)) {
            try {
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (TAG.equals(parser.getName())) {
                        trips.add(getFromXml(parser));
                    } else {
                        XmlUtils.skipTag(parser);
                    }
                }
            } catch (XmlPullParserException ignored) {
                ignored.printStackTrace();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }

        return trips;
    }
}
