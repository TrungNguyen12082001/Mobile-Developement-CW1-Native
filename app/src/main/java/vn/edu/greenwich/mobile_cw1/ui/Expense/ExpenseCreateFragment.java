package vn.edu.greenwich.mobile_cw1.ui.Expense;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.models.Expense;
import vn.edu.greenwich.mobile_cw1.ui.dialog.DatePickerFragment;
import vn.edu.greenwich.mobile_cw1.ui.dialog.TimePickerFragment;

public class ExpenseCreateFragment extends DialogFragment
        implements DatePickerFragment.FragmentListener, TimePickerFragment.FragmentListener {
    protected long _tripId;

    protected EditText fmRequestCreateDate, fmRequestCreateTime, fmRequestCreateContent, fmRequestCreateAmount, fmRequestCreateTypeExpense;
    protected TextView fmExpenseAddError;
    protected Button fmRequestCreateButtonCancel, fmRequestCreateButtonAdd;
    protected Spinner fmRequestCreateType;

    public ExpenseCreateFragment() {
        _tripId = -1;
    }

    public ExpenseCreateFragment(long tripId) {
        _tripId = tripId;
    }

    @Override
    public void sendFromDatePickerFragment(String date) {
        fmRequestCreateDate.setText(date);
    }

    @Override
    public void sendFromTimePickerFragment(String time) {
        fmRequestCreateTime.setText(time);
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_create, container, false);

        fmExpenseAddError = view.findViewById(R.id.fmExpenseAddError);
        fmRequestCreateDate = view.findViewById(R.id.fmRequestCreateDate);
        fmRequestCreateTime = view.findViewById(R.id.fmRequestCreateTime);
        fmRequestCreateContent = view.findViewById(R.id.fmRequestCreateContent);
        fmRequestCreateAmount = view.findViewById(R.id.fmRequestCreateAmount);
        fmRequestCreateTypeExpense = view.findViewById(R.id.fmRequestCreateTypeExpense);
        fmRequestCreateButtonCancel = view.findViewById(R.id.fmRequestCreateButtonCancel);
        fmRequestCreateButtonAdd = view.findViewById(R.id.fmRequestCreateButtonAdd);
        fmRequestCreateType = view.findViewById(R.id.fmRequestCreateType);

        fmRequestCreateButtonCancel.setOnClickListener(v -> dismiss());
        fmRequestCreateButtonAdd.setOnClickListener(v -> createRequest());

        fmRequestCreateDate.setOnTouchListener((v, motionEvent) -> showDateDialog(motionEvent));
        fmRequestCreateTime.setOnTouchListener((v, motionEvent) -> showTimeDialog(motionEvent));

        setTypeSpinner();

        return view;
    }

    protected void setTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.request_type,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fmRequestCreateType.setAdapter(adapter);
    }

    protected boolean showDateDialog(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new DatePickerFragment().show(getChildFragmentManager(), null);
            return true;
        }

        return false;
    }

    protected boolean showTimeDialog(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new TimePickerFragment().show(getChildFragmentManager(), null);
            return true;
        }

        return false;
    }

    protected void createRequest() {
        if (isValidFormRequest()) {
            Expense request = new Expense();

            request.setType(fmRequestCreateType.getSelectedItem().toString());
            request.setTime(fmRequestCreateTime.getText().toString());
            request.setDate(fmRequestCreateDate.getText().toString());
//            request.setContent(fmRequestCreateContent.getText().toString());
            request.setAmount(fmRequestCreateAmount.getText().toString());
            request.setContent(fmRequestCreateTypeExpense.getText().toString());

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromRequestCreateFragment(request);

            dismiss();
        }
    }

    protected boolean isValidFormRequest() {
        boolean isValid = true;

        String error = "";
        String amount = fmRequestCreateAmount.getText().toString();
        String type = fmRequestCreateTypeExpense.getText().toString();

        if (amount == null || amount.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_amount_expense) + "\n";
            isValid = false;
        }

        if (type == null || type.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_type_expense) + "\n";
            isValid = false;
        }

        fmExpenseAddError.setText(error);

        return isValid;
    }

    public interface FragmentListener {
        void sendFromRequestCreateFragment(Expense request);
    }
}