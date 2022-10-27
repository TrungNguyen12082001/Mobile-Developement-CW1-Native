package vn.edu.greenwich.mobile_cw1.ui.Trip;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.models.Trip;
import vn.edu.greenwich.mobile_cw1.ui.dialog.CalendarFragment;

public class TripSearchFragment extends DialogFragment implements CalendarFragment.FragmentListener {
    protected EditText fmTripSearchDate, fmTripSearchName;
    protected Button fmTripSearchBtnCancel, fmTripSearchBtnSearch;

    public TripSearchFragment() {}

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
        View view = inflater.inflate(R.layout.fragment_trip_search, container, false);

        fmTripSearchDate = view.findViewById(R.id.fmTripSearchDate);
        fmTripSearchName = view.findViewById(R.id.fmTripSearchName);
        fmTripSearchBtnCancel = view.findViewById(R.id.fmTripSearchBtnCancel);
        fmTripSearchBtnSearch = view.findViewById(R.id.fmTripSearchBtnSearch);

        fmTripSearchBtnSearch.setOnClickListener(v -> search());
        fmTripSearchBtnCancel.setOnClickListener(v -> dismiss());
        fmTripSearchDate.setOnTouchListener((v, motionEvent) -> showCalendar(motionEvent));

        return view;
    }

    protected void search() {
        Trip _resident = new Trip();

        String date = fmTripSearchDate.getText().toString();
        String name = fmTripSearchName.getText().toString();

        if (date != null && !date.trim().isEmpty())
            _resident.setStartDate(date);

        if (name != null && !name.trim().isEmpty())
            _resident.setName(name);

        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromResidentSearchFragment(_resident);

        dismiss();
    }

    protected boolean showCalendar(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    @Override
    public void sendFromCalendarFragment(String date) {
        fmTripSearchDate.setText(date);
    }

    public interface FragmentListener {
        void sendFromResidentSearchFragment(Trip resident);
    }
}