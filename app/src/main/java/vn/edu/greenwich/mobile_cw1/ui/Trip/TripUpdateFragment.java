package vn.edu.greenwich.mobile_cw1.ui.Trip;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.models.Trip;

public class TripUpdateFragment extends Fragment implements TripRegisterFragment.FragmentListener {
    public static final String ARG_PARAM_RESIDENT = "trip";

    public TripUpdateFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_update, container, false);

        if (getArguments() != null) {
            Trip resident = (Trip) getArguments().getSerializable(ARG_PARAM_RESIDENT);

            Bundle bundle = new Bundle();
            bundle.putSerializable(TripRegisterFragment.ARG_PARAM_RESIDENT, resident);

            // Send arguments (resident info) to ResidentRegisterFragment.
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }

        return view;
    }

    @Override
    public void sendFromResidentRegisterFragment(long status) {
        switch ((int) status) {
            case 0:
                Toast.makeText(getContext(), R.string.notification_update_fail, Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), R.string.notification_update_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();
        }
    }
}