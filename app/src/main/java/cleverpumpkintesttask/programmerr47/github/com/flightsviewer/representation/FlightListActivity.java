package cleverpumpkintesttask.programmerr47.github.com.flightsviewer.representation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;


public class FlightListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_list_page);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new FlightListFragment();
        }

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
