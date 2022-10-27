package vn.edu.greenwich.mobile_cw1.ui.Expense.list;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.database.ResimaDAO;
import vn.edu.greenwich.mobile_cw1.models.Expense;

public class ExpenseListFragment extends Fragment {
    public static final String ARG_PARAM_RESIDENT_ID = "trip_id";

    protected ArrayList<Expense> _requestList = new ArrayList<>();

    protected ResimaDAO _db;
    protected TextView fmRequestListEmptyNotice;
    protected RecyclerView fmRequestListRecylerView;

    public ExpenseListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        _db = new ResimaDAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        if (getArguments() != null) {
            Expense request = new Expense();
            request.setTripId(getArguments().getLong(ARG_PARAM_RESIDENT_ID));

            _requestList = _db.getExpenseList(request, null, false);
        }

        fmRequestListRecylerView = view.findViewById(R.id.fmRequestListRecylerView);
        fmRequestListEmptyNotice = view.findViewById(R.id.fmRequestListEmptyNotice);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        fmRequestListRecylerView.addItemDecoration(dividerItemDecoration);
        fmRequestListRecylerView.setAdapter(new ExpenseAdapter(_requestList));
        fmRequestListRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Show "No Request." message.
        fmRequestListEmptyNotice.setVisibility(_requestList.isEmpty() ? View.VISIBLE : View.GONE);

        return view;
    }
}