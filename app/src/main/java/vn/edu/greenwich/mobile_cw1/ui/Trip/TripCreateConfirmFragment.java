package vn.edu.greenwich.mobile_cw1.ui.Trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Trip;

public class TripCreateConfirmFragment extends DialogFragment {
    protected ResimaDAO _db;
    protected Trip _trip;
    protected Button fmResidentRegisterConfirmButtonConfirm, fmResidentRegisterConfirmButtonCancel;
    protected TextView fmResidentRegisterConfirmName, fmResidentRegisterConfirmStartDate, fmResidentRegisterConfirmOwner, fmDepartureConfirm, fmDestinationConfirm, fmRiskAssessmentConfirm, fmDescConfirm, fmParticipantsConfirm;

    public TripCreateConfirmFragment() {
        _trip = new Trip();
    }

    public TripCreateConfirmFragment(Trip resident) {
        _trip = resident;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new ResimaDAO(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_create_confirm, container, false);

        String name = getString(R.string.error_no_info);
        String startDate = getString(R.string.error_no_info);
        String departure = getString(R.string.error_no_info);
        String destination = getString(R.string.error_no_info);
        String riskAssessment = getString(R.string.error_no_info);
        String desc = getString(R.string.error_no_info);
        String participants = getString(R.string.error_no_info);
        String ownerType = getString(R.string.error_no_info);

        fmResidentRegisterConfirmName = view.findViewById(R.id.fmResidentRegisterConfirmName);
        fmResidentRegisterConfirmStartDate = view.findViewById(R.id.fmResidentRegisterConfirmStartDate);
        fmDepartureConfirm = view.findViewById(R.id.fmDepartureConfirm);
        fmDestinationConfirm = view.findViewById(R.id.fmDestinationConfirm);
        fmRiskAssessmentConfirm = view.findViewById(R.id.fmRiskAssessmentConfirm);
        fmDescConfirm = view.findViewById(R.id.fmDescConfirm);
        fmParticipantsConfirm = view.findViewById(R.id.fmParticipantsConfirm);
        fmResidentRegisterConfirmOwner = view.findViewById(R.id.fmResidentRegisterConfirmOwner);
        fmResidentRegisterConfirmButtonCancel = view.findViewById(R.id.fmResidentRegisterConfirmButtonCancel);
        fmResidentRegisterConfirmButtonConfirm = view.findViewById(R.id.fmResidentRegisterConfirmButtonConfirm);

//        if (_resident.getOwner() != -1) {
//            ownerType = _resident.getOwner() == 1 ? getString(R.string.label_owner) : getString(R.string.label_tenant);
//        }

        if (_trip.getName() != null && !_trip.getName().trim().isEmpty()) {
            name = _trip.getName();
        }

        if (_trip.getStartDate() != null && !_trip.getStartDate().trim().isEmpty()) {
            startDate = _trip.getStartDate();
        }

        if (_trip.getDeparture() != null && !_trip.getDeparture().trim().isEmpty()) {
            departure = _trip.getDeparture();
        }

        if (_trip.getDestination() != null && !_trip.getDestination().trim().isEmpty()) {
            destination = _trip.getDestination();
        }

        if (_trip.getRiskAssessment() != null && !_trip.getRiskAssessment().trim().isEmpty()) {
            riskAssessment = _trip.getRiskAssessment();
        }

        if (_trip.getDesc() != null && !_trip.getDesc().trim().isEmpty()) {
            desc = _trip.getDesc();
        }

        if (_trip.getParticipants() != null && !_trip.getParticipants().trim().isEmpty()) {
            participants = _trip.getParticipants();
        }

        fmResidentRegisterConfirmName.setText(name);
        fmResidentRegisterConfirmStartDate.setText(startDate);
        fmDepartureConfirm.setText(departure);
        fmDestinationConfirm.setText(destination);
        fmRiskAssessmentConfirm.setText(riskAssessment);
        fmDescConfirm.setText(desc);
        fmParticipantsConfirm.setText(participants);
        fmResidentRegisterConfirmOwner.setText(ownerType);

        fmResidentRegisterConfirmButtonCancel.setOnClickListener(v -> dismiss());
        fmResidentRegisterConfirmButtonConfirm.setOnClickListener(v -> confirm());

        return view;
    }

    protected void confirm() {
        long status = _db.insertTrip(_trip);

        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromResidentRegisterConfirmFragment(status);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromResidentRegisterConfirmFragment(long status);
    }
}