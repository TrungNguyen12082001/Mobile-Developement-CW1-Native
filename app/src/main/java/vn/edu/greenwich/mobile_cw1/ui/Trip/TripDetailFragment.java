package vn.edu.greenwich.mobile_cw1.ui.Trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Expense;
import vn.edu.greenwich.mobile_cw1.models.Trip;
import vn.edu.greenwich.mobile_cw1.ui.dialog.DeleteConfirmFragment;
import vn.edu.greenwich.mobile_cw1.ui.Expense.ExpenseCreateFragment;
import vn.edu.greenwich.mobile_cw1.ui.Expense.list.ExpenseListFragment;

public class TripDetailFragment extends Fragment
        implements DeleteConfirmFragment.FragmentListener, ExpenseCreateFragment.FragmentListener {
    public static final String ARG_PARAM_RESIDENT = "trip";

    protected ResimaDAO _db;
    protected Trip _trip;
    protected Button fmResidentDetailRequestButton;
    protected BottomAppBar fmResidentDetailBottomAppBar;
    protected FragmentContainerView fmResidentDetailRequestList;
    protected TextView fmResidentDetailName, fmResidentDetailStartDate, fmResidentDetailOwner, fmDetailDeparture, fmDetailDestination, fmDetailRiskAssessment, fmDetailDesc, fmDetailParticipants;

    public TripDetailFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        fmResidentDetailName = view.findViewById(R.id.fmResidentDetailName);
        fmResidentDetailStartDate = view.findViewById(R.id.fmResidentDetailStartDate);
        fmDetailDeparture = view.findViewById(R.id.fmDetailDeparture);
        fmDetailDestination = view.findViewById(R.id.fmDetailDestination);
        fmDetailRiskAssessment = view.findViewById(R.id.fmDetailRiskAssessment);
        fmDetailDesc = view.findViewById(R.id.fmDetailDesc);
        fmDetailParticipants = view.findViewById(R.id.fmDetailParticipants);
        fmResidentDetailOwner = view.findViewById(R.id.fmResidentDetailOwner);
        fmResidentDetailBottomAppBar = view.findViewById(R.id.fmResidentDetailBottomAppBar);
        fmResidentDetailRequestButton = view.findViewById(R.id.fmResidentDetailRequestButton);
        fmResidentDetailRequestList = view.findViewById(R.id.fmResidentDetailRequestList);

        fmResidentDetailBottomAppBar.setOnMenuItemClickListener(item -> menuItemSelected(item));
        fmResidentDetailRequestButton.setOnClickListener(v -> showAddRequestFragment());

        showDetails();
        showRequestList();

        return view;
    }

    protected void showDetails() {
        String name = getString(R.string.error_not_found);
        String startDate = getString(R.string.error_not_found);
        String departure = getString(R.string.error_not_found);
        String destination = getString(R.string.error_not_found);
        String riskAssessment = getString(R.string.error_not_found);
        String desc = getString(R.string.error_not_found);
        String participants = getString(R.string.error_not_found);
        String owner = getString(R.string.error_not_found);

        if (getArguments() != null) {
            _trip = (Trip) getArguments().getSerializable(ARG_PARAM_RESIDENT);
            _trip = _db.getTripById(_trip.getId()); // Retrieve data from Database.

            name = _trip.getName();
            startDate = _trip.getStartDate();
            departure = _trip.getDeparture();
            destination = _trip.getDestination();
            riskAssessment = _trip.getRiskAssessment();
            desc = _trip.getDesc();
            participants = _trip.getParticipants();
//            owner = _resident.getOwner() == 1 ? getString(R.string.label_owner) : getString(R.string.label_tenant);
        }

        fmResidentDetailName.setText(name);
        fmResidentDetailStartDate.setText(startDate);
        fmDetailDeparture.setText(departure);
        fmDetailDestination.setText(destination);
        fmDetailRiskAssessment.setText(riskAssessment);
        fmDetailDesc.setText(desc);
        fmDetailParticipants.setText(participants);
        fmResidentDetailOwner.setText(owner);
    }

    protected void showRequestList() {
        if (getArguments() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExpenseListFragment.ARG_PARAM_RESIDENT_ID, _trip.getId());

            // Send arguments (resident id) to RequestListFragment.
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }
    }

    protected boolean menuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.residentUpdateFragment:
                showUpdateFragment();
                return true;

            case R.id.residentDeleteFragment:
                showDeleteConfirmFragment();
                return true;
        }

        return true;
    }

    protected void showUpdateFragment() {
        Bundle bundle = null;

        if (_trip != null) {
            bundle = new Bundle();
            bundle.putSerializable(TripUpdateFragment.ARG_PARAM_RESIDENT, _trip);
        }

        Navigation.findNavController(getView()).navigate(R.id.residentUpdateFragment, bundle);
    }

    protected void showDeleteConfirmFragment() {
        new DeleteConfirmFragment(getString(R.string.notification_delete_confirm)).show(getChildFragmentManager(), null);
    }

    protected void showAddRequestFragment() {
        new ExpenseCreateFragment(_trip.getId()).show(getChildFragmentManager(), null);
    }

    @Override
    public void sendFromDeleteConfirmFragment(int status) {
        if (status == 1 && _trip != null) {
            long numOfDeletedRows = _db.deleteTrip(_trip.getId());

            if (numOfDeletedRows > 0) {
                Toast.makeText(getContext(), R.string.notification_delete_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();

                return;
            }
        }

        Toast.makeText(getContext(), R.string.notification_delete_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFromRequestCreateFragment(Expense request) {
        if (request != null) {
            request.setTripId(_trip.getId());

            long id = _db.insertExpense(request);

            Toast.makeText(getContext(), id == -1 ? R.string.notification_create_fail : R.string.notification_create_success, Toast.LENGTH_SHORT).show();

            reloadRequestList();
        }
    }

    protected void reloadRequestList() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseListFragment.ARG_PARAM_RESIDENT_ID, _trip.getId());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fmResidentDetailRequestList, ExpenseListFragment.class, bundle)
                .commit();
    }
}