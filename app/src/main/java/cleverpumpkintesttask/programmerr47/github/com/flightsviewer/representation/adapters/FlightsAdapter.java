package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightInfo;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightPoint;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Price;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.Util;

/**
 * Simple realisation of {@link BindBaseAdapter} sharpened on flights.
 * Uses {@link cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary}
 * as items of list.
 *
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class FlightsAdapter extends BindBaseAdapter {
    private static final int FLIGHT_ITEM_ID = R.layout.flight_list_item;

    private Context mContext;
    private List<TripSummary> mItems;

    public FlightsAdapter(Context context, List<TripSummary> items) {
        if (context == null) {
            throw new NullPointerException("Context must be not null");
        }

        if (items == null) {
            throw new NullPointerException("Items must be not null");
        }

        mContext = context;
        mItems = items;
    }

    @Override
    protected View newView(ViewGroup parent, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(FLIGHT_ITEM_ID, parent, false);
        ViewHolder holder = new ViewHolder();

        holder.flightCarrier = (TextView) view.findViewById(R.id.flightCarrier);
        holder.flightPrice = (TextView) view.findViewById(R.id.flightPrice);
        holder.takeoffCity = (TextView) view.findViewById(R.id.takeoffCity);
        holder.takeoffDate = (TextView) view.findViewById(R.id.takeoffDate);
        holder.landingCity = (TextView) view.findViewById(R.id.landingCity);
        holder.landingDate = (TextView) view.findViewById(R.id.landingDate);

        view.setTag(holder);

        return view;
    }

    @Override
    protected void bindView(View view, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        TripSummary tripSummary = mItems.get(position);

        FlightInfo flightInfo = tripSummary.getFlight();
        if ((flightInfo != null) && (flightInfo.getCarrier() != null)) {
            holder.flightCarrier.setText(flightInfo.getCarrier());
        } else {
            holder.flightCarrier.setText(R.string.UNKNOWN_CARRIER);
        }

        Price price = tripSummary.getPrice();
        if ((price != null) && (price.getValue() != 0.0)) {
            holder.flightPrice.setText(String.format(mContext.getString(R.string.PRICE_VALUE), Util.convertPriceToString(price.getValue())));
        } else {
            holder.flightPrice.setText(R.string.UNKNOWN_PRICE);
        }

        setInfoToFlightPoint(tripSummary.getTakeoff(), holder.takeoffCity, holder.takeoffDate);
        setInfoToFlightPoint(tripSummary.getLanding(), holder.landingCity, holder.landingDate);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refreshItems(List<TripSummary> items) {
        if (items == null) {
            throw new NullPointerException("Items must be not null");
        }

        mItems = items;
        notifyDataSetChanged();
    }

    private void setInfoToFlightPoint(FlightPoint flightPoint, TextView city, TextView date) {
        if (flightPoint != null) {
            if (flightPoint.getCity() != null) {
                city.setText(flightPoint.getCity());
            } else {
                city.setText(R.string.UNKNOWN_CITY);
            }

            if ((flightPoint.getTime() != null) || (flightPoint.getDate() != null)) {
                if (flightPoint.getTime() == null) {
                    date.setText(flightPoint.getDate());
                } else if (flightPoint.getDate() == null) {
                    date.setText(flightPoint.getTime());
                } else {
                    date.setText(flightPoint.getTime() + ", " + flightPoint.getDate());
                }
            } else {
                date.setText(R.string.UNKNOWN_DATE);
            }
        } else {
            city.setText(R.string.UNKNOWN_CITY);
            date.setText(R.string.UNKNOWN_DATE);
        }
    }

    public static class ViewHolder {
        public TextView flightCarrier;
        public TextView flightPrice;
        public TextView takeoffCity;
        public TextView takeoffDate;
        public TextView landingCity;
        public TextView landingDate;
    }
}
