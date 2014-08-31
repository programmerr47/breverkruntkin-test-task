package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.Trip;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Description;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightInfo;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.FlightPoint;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.Price;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.clever_api.responseObjects.summary.TripSummary;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.AsyncTaskWithListener;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.GetBitmapFromUrlTask;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.tasks.GetTripFullInfoTask;
import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation.view.FloatingActionButton;

/**
 * @author Michael Spitsin
 * @since 2014-08-31
 */
public class FlightInfoFragment extends Fragment implements View.OnClickListener, AsyncTaskWithListener.OnTaskFinishedListener {
    private static final String BEGINNING_DATA = "BEGINNING_DATA";

    private TripSummary mSummaryInfo;
    private Trip mFullInfo;
    private GetTripFullInfoTask mGetTripFullInfoTask;
    private GetBitmapFromUrlTask mGetBitmapFromUrlTask;
    private Bitmap mFlightCoverBmp;

    private ImageView mFlightCover;
    private TextView mCheckConnectionHint;
    private ProgressBar mLoadingProgress;
    private TextView mFlightFrom;
    private TextView mFlightTo;
    private TextView mFlightCarrier;
    private TextView mFlightPrice;
    private TextView mTakeoffTime;
    private TextView mTakeoffDate;
    private TextView mTakeoffCity;
    private TextView mLandingTime;
    private TextView mLandingDate;
    private TextView mLandingCity;
    private TextView mFlightDescription;
    private TextView mFlightDuration;
    private TextView mFlightNumber;
    private TextView mTypeOfPlane;
    private FloatingActionButton mBuyTicketButton;

    public static FlightInfoFragment newInstance(TripSummary beginData) {
        FlightInfoFragment fragment = new FlightInfoFragment();

        Bundle args = new Bundle();
        args.putParcelable(BEGINNING_DATA, beginData);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mSummaryInfo = getArguments().getParcelable(BEGINNING_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flight_list_item_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mFlightCover = (ImageView) view.findViewById(R.id.flightCover);
        mCheckConnectionHint = (TextView) view.findViewById(R.id.checkConnectionHint);
        mLoadingProgress = (ProgressBar) view.findViewById(R.id.loadingProgress);
        mFlightFrom = (TextView) view.findViewById(R.id.flightFrom);
        mFlightTo = (TextView) view.findViewById(R.id.flightTo);
        mFlightCarrier = (TextView) view.findViewById(R.id.flightCarrier);
        mFlightPrice = (TextView) view.findViewById(R.id.flightPrice);
        mTakeoffTime = (TextView) view.findViewById(R.id.takeoffTime);
        mTakeoffDate = (TextView) view.findViewById(R.id.takeoffDate);
        mTakeoffCity = (TextView) view.findViewById(R.id.takeoffCity);
        mLandingTime = (TextView) view.findViewById(R.id.landingTime);
        mLandingDate = (TextView) view.findViewById(R.id.landingDate);
        mLandingCity = (TextView) view.findViewById(R.id.landingCity);
        mFlightDescription = (TextView) view.findViewById(R.id.flightDescription);
        mFlightDuration = (TextView) view.findViewById(R.id.flightDuration);
        mFlightNumber = (TextView) view.findViewById(R.id.flightNumber);
        mTypeOfPlane = (TextView) view.findViewById(R.id.typeOfPlane);
        mBuyTicketButton = (FloatingActionButton) view.findViewById(R.id.buyTicketButton);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mFullInfo == null) {
            representTakeoffInfo(mSummaryInfo.getTakeoff());
            representLandingInfo(mSummaryInfo.getLanding());
            representPriceInfo(mSummaryInfo.getPrice());
            representFlightInfo(mSummaryInfo.getFlight());
            representFlightDuration(mSummaryInfo.getDuration());
            mFlightDescription.setText(R.string.LOADING_DESCRIPTION);
            mFlightCover.setVisibility(View.GONE);
            mLoadingProgress.setVisibility(View.VISIBLE);
            loadFullInfo();
        } else {
            representTakeoffInfo(mFullInfo.getTakeoff());
            representLandingInfo(mFullInfo.getLanding());
            representPriceInfo(mFullInfo.getPrice());
            representFlightInfo(mFullInfo.getFlight());
            representFlightDuration(mFullInfo.getDuration());
            representFlightDescription(mFullInfo.getDescription());

            if (mFlightCoverBmp == null) {
                loadFlightCover();
                mFlightCover.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.VISIBLE);
            } else {
                mFlightCover.setImageBitmap(mFlightCoverBmp);
                mFlightCover.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.GONE);
            }
        }
        mCheckConnectionHint.setVisibility(View.GONE);

        mBuyTicketButton.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mGetTripFullInfoTask != null) {
            mGetTripFullInfoTask.setOnTaskFinishedListener(null);
            mGetTripFullInfoTask.cancel(true);
        }
        if (mGetBitmapFromUrlTask != null) {
            mGetBitmapFromUrlTask.setOnTaskFinishedListener(null);
            mGetBitmapFromUrlTask.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buyTicketButton) {
            Toast.makeText(getActivity(), R.string.BUY_TICKET_RESULT, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskFinished(String taskName, Object extraObject) {
        if (GetTripFullInfoTask.class.getName().equals(taskName)) {
            if (extraObject != null) {
                mFullInfo = (Trip) extraObject;

                representTakeoffInfo(mFullInfo.getTakeoff());
                representLandingInfo(mFullInfo.getLanding());
                representPriceInfo(mFullInfo.getPrice());
                representFlightInfo(mFullInfo.getFlight());
                representFlightDuration(mFullInfo.getDuration());
                representFlightDescription(mFullInfo.getDescription());
                mCheckConnectionHint.setVisibility(View.GONE);
                loadFlightCover();
            } else {
                mCheckConnectionHint.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.GONE);
                mFlightDescription.setText(R.string.UNKNOWN_DESCRIPTION);
            }
        } else if (GetBitmapFromUrlTask.class.getName().equals(taskName)) {
            if (extraObject != null) {
                mFlightCoverBmp = (Bitmap) extraObject;
                mFlightCover.setImageBitmap(mFlightCoverBmp);
                mFlightCover.setVisibility(View.VISIBLE);

                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(200);
                mFlightCover.startAnimation(animation);
            } else {
                mFlightCover.setVisibility(View.GONE);
            }

            mLoadingProgress.setVisibility(View.GONE);
        }
    }

    private void loadFullInfo() {
        mGetTripFullInfoTask = new GetTripFullInfoTask();
        mGetTripFullInfoTask.setOnTaskFinishedListener(this);
        mGetTripFullInfoTask.execute(mSummaryInfo);
    }

    private void loadFlightCover() {
        if ((mFullInfo.getPhoto() != null) && (mFullInfo.getPhoto().getSource() != null)) {
            mGetBitmapFromUrlTask = new GetBitmapFromUrlTask();
            mGetBitmapFromUrlTask.setOnTaskFinishedListener(this);
            mGetBitmapFromUrlTask.execute(mFullInfo.getPhoto().getSource());
        }
    }

    private void representTakeoffInfo(FlightPoint takeoff) {
        if (takeoff != null) {
            String city = takeoff.getCity();
            if (city != null) {
                mFlightFrom.setText(city);
                mTakeoffCity.setText(city);
            } else {
                mFlightFrom.setText(R.string.UNKNOWN_CITY);
                mTakeoffCity.setText(R.string.UNKNOWN_CITY);
            }

            String date = takeoff.getDate();
            if (date != null) {
                mTakeoffDate.setText(date);
            } else {
                mTakeoffDate.setText(R.string.UNKNOWN_DATE);
            }

            String time = takeoff.getTime();
            if (time != null) {
                mTakeoffTime.setText(time);
            } else {
                mTakeoffTime.setText(R.string.UNKNOWN_TIME);
            }
        } else {
            mFlightFrom.setText(R.string.UNKNOWN_CITY);
            mTakeoffCity.setText(R.string.UNKNOWN_CITY);
            mTakeoffDate.setText(R.string.UNKNOWN_DATE);
            mTakeoffTime.setText(R.string.UNKNOWN_TIME);
        }
    }

    private void representLandingInfo(FlightPoint landing) {
        if (landing != null) {
            String city = landing.getCity();
            if (city != null) {
                mFlightTo.setText(city);
                mLandingCity.setText(city);
            } else {
                mFlightTo.setText(R.string.UNKNOWN_CITY);
                mLandingCity.setText(R.string.UNKNOWN_CITY);
            }

            String date = landing.getDate();
            if (date != null) {
                mLandingDate.setText(date);
            } else {
                mLandingDate.setText(R.string.UNKNOWN_DATE);
            }

            String time = landing.getTime();
            if (time != null) {
                mLandingTime.setText(time);
            } else {
                mLandingTime.setText(R.string.UNKNOWN_TIME);
            }
        } else {
            mFlightTo.setText(R.string.UNKNOWN_CITY);
            mLandingCity.setText(R.string.UNKNOWN_CITY);
            mLandingDate.setText(R.string.UNKNOWN_DATE);
            mLandingTime.setText(R.string.UNKNOWN_TIME);
        }
    }

    private void representPriceInfo(Price price) {
        if ((price != null) && (price.getValue() != 0.0)) {
            mFlightPrice.setText(Util.convertPriceToString(getActivity(), price.getValue()));
        } else {
            mFlightPrice.setText(R.string.UNKNOWN_PRICE);
        }
    }

    private void representFlightInfo(FlightInfo flightInfo) {
        if (flightInfo != null) {
            String carrier = flightInfo.getCarrier();
            if (carrier != null) {
                mFlightCarrier.setText(String.format(getActivity().getString(R.string.FLIGHT_PROVIDED_BY), carrier));
            } else {
                mFlightCarrier.setText(String.format(getActivity().getString(R.string.FLIGHT_PROVIDED_BY), getActivity().getString(R.string.UNKNOWN_CARRIER)));
            }

            String eq = flightInfo.getEq();
            if (carrier != null) {
                mTypeOfPlane.setText(eq);
            } else {
                mTypeOfPlane.setText(R.string.UNKNOWN_PLANE_TYPE);
            }

            String number = flightInfo.getNumber();
            if (carrier != null) {
                mFlightNumber.setText(number);
            } else {
                mFlightNumber.setText(R.string.UNKNOWN_FLIGHT_NUMBER);
            }
        } else {
            mFlightCarrier.setText(String.format(getActivity().getString(R.string.FLIGHT_PROVIDED_BY), getActivity().getString(R.string.UNKNOWN_CARRIER)));
            mTypeOfPlane.setText(R.string.UNKNOWN_PLANE_TYPE);
            mFlightCarrier.setText(R.string.UNKNOWN_FLIGHT_NUMBER);
        }
    }

    private void representFlightDuration(String duration) {
        if (duration != null) {
            mFlightDuration.setText(duration);
        } else {
            mFlightDuration.setText(R.string.UNKNOWN_DURATION);
        }
    }

    private void representFlightDescription(Description description) {
        if ((description != null) && (description.getValue() != null)) {
            mFlightDescription.setText(description.getValue());
        } else {
            mFlightDescription.setText(R.string.UNKNOWN_DESCRIPTION);
        }
    }
}
