package vn.edu.greenwich.mobile_cw1.ui.Trip.list;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Trip;
import vn.edu.greenwich.mobile_cw1.ui.Trip.TripSearchFragment;

public class TripListFragment extends Fragment implements TripSearchFragment.FragmentListener {
    protected ArrayList<Trip> residentList = new ArrayList<>();

    protected ResimaDAO _db;
    protected EditText fmTripListFilter;
    protected TextView fmResidentListEmptyNotice;
    protected RecyclerView fmResidentListRecylerView;
    protected TripAdapter tripAdapter;
    protected ImageButton fmTripListBtnSearch, fmTripListBtnResetSearch;

    public TripListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        _db = new ResimaDAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        fmResidentListRecylerView = view.findViewById(R.id.fmResidentListRecylerView);
        fmResidentListEmptyNotice = view.findViewById(R.id.fmResidentListEmptyNotice);

        fmTripListBtnResetSearch = view.findViewById(R.id.fmTripListBtnResetSearch);
        fmTripListBtnResetSearch.setOnClickListener(v -> resetSearch());

        fmTripListBtnSearch = view.findViewById(R.id.fmTripListBtnSearch);
        fmTripListBtnSearch.setOnClickListener(v -> showSearchDialog());

        fmTripListFilter = view.findViewById(R.id.fmTripListFilter);
        fmTripListFilter.addTextChangedListener(filter());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        tripAdapter = new TripAdapter(residentList);

        fmResidentListRecylerView.addItemDecoration(dividerItemDecoration);
        fmResidentListRecylerView.setAdapter(tripAdapter);
        fmResidentListRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        reloadList(null);
    }

    protected void reloadList(Trip resident) {
        residentList = _db.getTripList(resident, null, false);
        tripAdapter.updateList(residentList);

        // Show "No Resident." message.
        fmResidentListEmptyNotice.setVisibility(residentList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    protected TextWatcher filter() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tripAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    protected void resetSearch() {
        fmTripListFilter.setText("");
        reloadList(null);
    }

    protected void showSearchDialog() {
        new TripSearchFragment().show(getChildFragmentManager(), null);
    }

    @Override
    public void sendFromResidentSearchFragment(Trip resident) {
        if (!resident.isEmpty()) {
            reloadList(resident);
            return;
        }

        reloadList(null);
    }
}