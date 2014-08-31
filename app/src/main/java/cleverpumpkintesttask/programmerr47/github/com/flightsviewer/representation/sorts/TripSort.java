package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;

import java.util.Comparator;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators.FlightDurationAscendingComparator;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators.FlightDurationDescendingComparator;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators.FlightPriceAscendingComparator;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.comparators.FlightPriceDescendingComparator;

/**
 * @author Michael Spitsin
 * @since 2014-08-31
 */
public class TripSort {

    private static final String PREFS_KEY = "com.github.programmerr47.cleverpumpkintesttask.SORTS";
    private static final String SORT_TYPE_KEY = "SORT_TYPE_KEY";
    private static final String ORDER_TYPE_KEY = "ORDER_TYPE_KEY";

    private Context mContext;
    private TripSortType mSortType;
    private OrderType mOrderType;

    public TripSort(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be not null");
        }

        mContext = context;
        restore();
    }

    public void setSortType(TripSortType sortType) {
        if (sortType == null) {
            throw new IllegalArgumentException("SortType must be not null");
        }

        mSortType = sortType;
    }

    public void setOrderType(OrderType orderType) {
        if (orderType == null) {
            throw new IllegalArgumentException("OrderType must be not null");
        }

        mOrderType = orderType;
    }

    public TripSortType getSortType() {
        return mSortType;
    }

    public OrderType getOrderType() {
        return mOrderType;
    }

    public Comparator<TripSummary> getComparator() {
        if ((mSortType == null) || (mOrderType == null)) {
            return null;
        }

        switch (mSortType) {
            case DURATION:
                switch (mOrderType) {
                    case ASC:
                        return new FlightDurationAscendingComparator();
                    case DESC:
                        return new FlightDurationDescendingComparator();
                    default:
                        throw new IllegalArgumentException(mSortType + " " + mOrderType + ": for such type of sort there is no implementation");
                }
            case PRICE:
                switch (mOrderType) {
                    case ASC:
                        return new FlightPriceAscendingComparator();
                    case DESC:
                        return new FlightPriceDescendingComparator();
                    default:
                        throw new IllegalArgumentException(mSortType + " " + mOrderType + ": for such type of sort there is no implementation");
                }
            default:
                throw new IllegalArgumentException(mSortType + ": for such type of sort there is no implementation");
        }
    }

    public void save() {
        if ((mOrderType != null) && (mSortType != null)) {
            SharedPreferences prefs = mContext.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(SORT_TYPE_KEY, mSortType.toString());
            editor.putString(ORDER_TYPE_KEY, mOrderType.toString());
            editor.apply();
        }
    }

    public void restore() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        String sortTypeStr = prefs.getString(SORT_TYPE_KEY, null);
        String orderTypeStr = prefs.getString(ORDER_TYPE_KEY, null);

        if (sortTypeStr != null) {
            mSortType = TripSortType.valueOf(sortTypeStr);
        }

        if (orderTypeStr != null) {
            mOrderType = OrderType.valueOf(orderTypeStr);
        }
    }
}
