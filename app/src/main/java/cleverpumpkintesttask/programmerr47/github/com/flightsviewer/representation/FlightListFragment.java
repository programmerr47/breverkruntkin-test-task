package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.adapters.FlightsAdapter;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.AsyncTaskWithListener;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.GetTripsTask;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public class FlightListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AsyncTaskWithListener.OnTaskFinishedListener {

    private List<TripSummary> mTrips;

    private GetTripsTask mGetTripsTask;

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mTripListView;
    private FlightsAdapter mTripAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mRefreshLayout.setColorSchemeResources(R.color.orange_pure, R.color.orange_light, R.color.orange_light_very, R.color.orange_light);
        mRefreshLayout.setOnRefreshListener(this);

        if (mTrips != null) {
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
    public void onRefresh() {
        refreshList();
    }

    @Override
    public void onTaskFinished(String taskName, Object extraObject) {
        if (GetTripsTask.class.getName().equals(taskName)) {
            if (extraObject != null) {
                mTrips = (List<TripSummary>) extraObject;
                mTripAdapter.refreshItems(mTrips);
            }

            mRefreshLayout.setRefreshing(false);
        }
    }

    private void refreshList() {
        mGetTripsTask = new GetTripsTask();
        mGetTripsTask.setOnTaskFinishedListener(this);
        mGetTripsTask.execute();
    }
}
