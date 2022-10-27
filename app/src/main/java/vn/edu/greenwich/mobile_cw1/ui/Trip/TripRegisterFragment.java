package vn.edu.greenwich.mobile_cw1.ui.Trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Trip;
import vn.edu.greenwich.mobile_cw1.ui.dialog.CalendarFragment;

public class TripRegisterFragment extends Fragment
        implements TripCreateConfirmFragment.FragmentListener, CalendarFragment.FragmentListener {
    public static final String ARG_PARAM_RESIDENT = "trip";

    protected EditText fmResidentRegisterName, fmResidentRegisterStartDate, fmDeparture, fmDestination, fmRiskAssessment, fmDesc, fmParticipants;
    protected LinearLayout fmResidentRegisterLinearLayout;
//    protected SwitchMaterial fmResidentRegisterOwner;
    protected TextView fmResidentRegisterError;
    protected Button fmResidentRegisterButton;

    protected ResimaDAO _db;

    public TripRegisterFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_create, container, false);

        fmResidentRegisterError = view.findViewById(R.id.fmResidentRegisterError);
        fmResidentRegisterName = view.findViewById(R.id.fmResidentRegisterName);
        fmDeparture = view.findViewById(R.id.fmDeparture);
        fmDestination = view.findViewById(R.id.fmDestination);
        fmRiskAssessment = view.findViewById(R.id.fmRiskAssessment);
        fmResidentRegisterStartDate = view.findViewById(R.id.fmResidentRegisterStartDate);
        fmDesc = view.findViewById(R.id.fmDesc);
        fmParticipants = view.findViewById(R.id.fmParticipants);
//        fmResidentRegisterOwner = view.findViewById(R.id.fmResidentRegisterOwner);
        fmResidentRegisterButton = view.findViewById(R.id.fmResidentRegisterButton);
        fmResidentRegisterLinearLayout = view.findViewById(R.id.fmResidentRegisterLinearLayout);

        // Show Calendar for choosing a date.
        fmResidentRegisterStartDate.setOnTouchListener((v, motionEvent) -> showCalendar(motionEvent));

        // Update current trip.
        if (getArguments() != null) {
            Trip resident = (Trip) getArguments().getSerializable(ARG_PARAM_RESIDENT);

            fmResidentRegisterName.setText(resident.getName());
            fmResidentRegisterStartDate.setText(resident.getStartDate());
            fmDeparture.setText(resident.getDeparture());
            fmDestination.setText(resident.getDestination());
            fmRiskAssessment.setText(resident.getRiskAssessment());
            fmDesc.setText(resident.getDesc());
            fmParticipants.setText(resident.getParticipants());
//            fmResidentRegisterOwner.setChecked(resident.getOwner() == 1 ? true : false);

            fmResidentRegisterButton.setText(R.string.label_update);
            fmResidentRegisterButton.setOnClickListener(v -> update(resident.getId()));

            return view;
        }

        // Create new trip.
        fmResidentRegisterButton.setOnClickListener(v -> register());

        return view;
    }

    protected void register() {
        if (isValidForm()) {
            Trip resident = getResidentFromInput(-1);

            new TripCreateConfirmFragment(resident).show(getChildFragmentManager(), null);

            return;
        }

        moveButton();
    }

    protected void update(long id) {
        if (isValidForm()) {
            Trip resident = getResidentFromInput(id);

            long status = _db.updateTrip(resident);

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromResidentRegisterFragment(status);

            return;
        }

        moveButton();
    }

    protected boolean showCalendar(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    protected Trip getResidentFromInput(long id) {
        String name = fmResidentRegisterName.getText().toString();
        String startDate = fmResidentRegisterStartDate.getText().toString();
        String departure = fmDeparture.getText().toString();
        String destination = fmDestination.getText().toString();
        String riskAssessment = fmRiskAssessment.getText().toString();
        String desc = fmDesc.getText().toString();
        String participants = fmParticipants.getText().toString();
//        int owner = fmResidentRegisterOwner.isChecked() ? 1 : 0;

        return new Trip(id, name, startDate, departure, destination, riskAssessment, desc, participants);
    }

    protected boolean isValidForm() {
        boolean isValid = true;

        String error = "";
        String name = fmResidentRegisterName.getText().toString();
        String departure = fmDeparture.getText().toString();
        String destination = fmDestination.getText().toString();
        String riskAssessment = fmRiskAssessment.getText().toString();
        String startDate = fmResidentRegisterStartDate.getText().toString();

        if (name == null || name.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_name) + "\n";
            isValid = false;
        }

        if (departure == null || departure.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_departure) + "\n";
            isValid = false;
        }

        if (destination == null || destination.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_destination) + "\n";
            isValid = false;
        }

        if (riskAssessment == null || riskAssessment.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_risk_assessment) + "\n";
            isValid = false;
        }

        if (startDate == null || startDate.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_start_date) + "\n";
            isValid = false;
        }

        fmResidentRegisterError.setText(error);

        return isValid;
    }

    protected void moveButton() {
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) fmResidentRegisterButton.getLayoutParams();

        int linearLayoutPaddingLeft = fmResidentRegisterLinearLayout.getPaddingLeft();
        int linearLayoutPaddingRight = fmResidentRegisterLinearLayout.getPaddingRight();
        int linearLayoutWidth = fmResidentRegisterLinearLayout.getWidth() - linearLayoutPaddingLeft - linearLayoutPaddingRight;

        btnParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        btnParams.topMargin += fmResidentRegisterButton.getHeight();
        btnParams.leftMargin = btnParams.leftMargin == 0 ? linearLayoutWidth - fmResidentRegisterButton.getWidth() : 0;

        fmResidentRegisterButton.setLayoutParams(btnParams);
    }

    @Override
    public void sendFromResidentRegisterConfirmFragment(long status) {
        switch ((int) status) {
            case -1:
                Toast.makeText(getContext(), R.string.notification_create_fail, Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), R.string.notification_create_success, Toast.LENGTH_SHORT).show();

                fmResidentRegisterName.setText("");
                fmResidentRegisterStartDate.setText("");
                fmDeparture.setText("");
                fmDestination.setText("");
                fmRiskAssessment.setText("");
                fmDesc.setText("");
                fmParticipants.setText("");

                fmResidentRegisterName.requestFocus();
                fmDeparture.requestFocus();
                fmDestination.requestFocus();
                fmRiskAssessment.requestFocus();
                fmDesc.requestFocus();
                fmParticipants.requestFocus();
        }
    }

    @Override
    public void sendFromCalendarFragment(String date) {
        fmResidentRegisterStartDate.setText(date);
    }

    public interface FragmentListener {
        void sendFromResidentRegisterFragment(long status);
    }
}