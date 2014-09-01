package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.adapters.FlightsAdapter;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.sorts.OrderType;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.sorts.TripSort;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.sorts.TripSortType;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.tasks.AsyncTaskWithListener;
import com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.tasks.GetTripsTask;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class FlightListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AsyncTaskWithListener.OnTaskFinishedListener, AdapterView.OnItemClickListener {

    private List<TripSummary> mTrips;
    private GetTripsTask mGetTripsTask;
    private TripSort mTripSort;

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mTripListView;
    private ProgressBar mLoadingProgress;

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
        mLoadingProgress = (ProgressBar) view.findViewById(R.id.loadingProgress);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTripSort = new TripSort(getActivity());

        mRefreshLayout.setColorSchemeResources(R.color.orange_pure, R.color.orange_light, R.color.orange_light_very, R.color.orange_light);
        mRefreshLayout.setOnRefreshListener(this);

        if (mTrips != null) {
            if (mTripSort.getComparator() != null) {
                Collections.sort(mTrips, mTripSort.getComparator());
            }
            mTripAdapter = new FlightsAdapter(getActivity(), mTrips);
            mLoadingProgress.setVisibility(View.GONE);
        } else {
            mTripAdapter = new FlightsAdapter(getActivity(), new ArrayList<TripSummary>());

            mLoadingProgress.setVisibility(View.VISIBLE);
            refreshList();
        }

        mTripListView.setAdapter(mTripAdapter);
        mTripListView.setOnItemClickListener(this);
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
            } else {
                mTrips = new ArrayList<TripSummary>();

                if (getActivity() != null) {
                    Toast.makeText(getActivity(), R.string.CHECK_CONNECTION, Toast.LENGTH_LONG).show();
                }
            }
            resortList();

            mLoadingProgress.setVisibility(View.GONE);
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FlightInfoActivity.class);
        intent.putExtra(FlightInfoActivity.TRIP_SUMMARY_INFO, mTrips.toArray(new TripSummary[mTrips.size()]));
        intent.putExtra(FlightInfoActivity.CURRENT_OPENED_TRIP, position);
        getActivity().startActivity(intent);
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

        new AlertDialog.Builder(context)
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
