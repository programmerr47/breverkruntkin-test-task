package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.adapters.FlightsAdapter;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.OrderType;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.TripSort;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.sorts.TripSortType;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.AsyncTaskWithListener;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.GetTripsTask;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class FlightListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AsyncTaskWithListener.OnTaskFinishedListener {

    private List<TripSummary> mTrips;
    private GetTripsTask mGetTripsTask;
    private TripSort mTripSort;

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mTripListView;
    private FlightsAdapter mTripAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flight_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        mTripListView = (ListView) view.findViewById(R.id.flightsListView);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTripSort = new TripSort(getActivity());

        mRefreshLayout.setColorSchemeResources(R.color.orange_pure, R.color.orange_light, R.color.orange_light_very, R.color.orange_light);
        mRefreshLayout.setOnRefreshListener(this);

        if (mTrips != null) {
            Collections.sort(mTrips, mTripSort.getComparator());
            mTripAdapter = new FlightsAdapter(getActivity(), mTrips);
        } else {
            mTripAdapter = new FlightsAdapter(getActivity(), new ArrayList<TripSummary>());

            mRefreshLayout.setRefreshing(true);
            refreshList();
        }

        mTripListView.setAdapter(mTripAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mGetTripsTask != null) {
            mGetTripsTask.setOnTaskFinishedListener(null);
            mGetTripsTask.cancel(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.flight_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                showSortDialog(getActivity());
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    @Override
    public void onTaskFinished(String taskName, Object extraObject) {
        if (GetTripsTask.class.getName().equals(taskName)) {
            if (extraObject != null) {
                mTrips = (List<TripSummary>) extraObject;
                resortList();
            }

            mRefreshLayout.setRefreshing(false);
        }
    }

    private void refreshList() {
        mGetTripsTask = new GetTripsTask();
        mGetTripsTask.setOnTaskFinishedListener(this);
        mGetTripsTask.execute();
    }

    private void resortList() {
        if ((mTrips != null) && (mTripSort.getComparator() != null)) {
            Collections.sort(mTrips, mTripSort.getComparator());
        }

        mTripAdapter.refreshItems(mTrips);
    }

    private void showSortDialog(Context context) {
        int numberOfSorting;

        if (mTripSort.getSortType() != null) {
            switch (mTripSort.getSortType()) {
                case DURATION:
                    numberOfSorting = 0;
                    break;
                case PRICE:
                    numberOfSorting = 1;
                    break;
                default:
                    numberOfSorting = -1;
            }
        } else {
            numberOfSorting = -1;
        }

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setSingleChoiceItems(R.array.sorts, numberOfSorting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mTripSort.setSortType(TripSortType.DURATION);
                                break;
                            case 1:
                                mTripSort.setSortType(TripSortType.PRICE);
                                break;
                            default:
                                throw new IllegalArgumentException("No sorts for element with number " + which);
                        }
                    }
                })
                .setTitle(R.string.CHOOSE_SORT)
                .setPositiveButton(R.string.ASCENDING, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTripSort.setOrderType(OrderType.ASC);
                        mTripSort.save();
                        resortList();
                    }
                })
                .setNegativeButton(R.string.DESCENDING, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTripSort.setOrderType(OrderType.DESC);
                        mTripSort.save();
                        resortList();
                    }
                })
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mTripSort.restore();
                    }
                })
                .show();
    }
}