package vn.edu.greenwich.mobile_cw1.ui.Expense.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import vn.edu.greenwich.mobile_cw1.R;
import vn.edu.greenwich.mobile_cw1.models.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> implements Filterable {
//    protected ArrayList<Expense> _list;
    protected ArrayList<Expense> _originalList;
    protected ArrayList<Expense> _filteredList;
    protected ExpenseAdapter.ItemFilter _itemFilter = new ExpenseAdapter.ItemFilter();

//    public ExpenseAdapter(ArrayList<Expense> list) {
//        _list = list;
//    }

    public ExpenseAdapter(ArrayList<Expense> list) {
        _originalList = list;
        _filteredList = list;
    }

    public void updateList(ArrayList<Expense> list) {
        _originalList = list;
        _filteredList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense request = _filteredList.get(position);

        holder.listItemRequestDate.setText(request.getDate());
        holder.listItemRequestTime.setText(request.getTime());
        holder.listItemRequestAmount.setText(request.getAmount());
        holder.listItemRequestTypeExpense.setText(request.getContent());
    }

    @Override
    public int getItemCount() {
        return _filteredList == null ? 0 : _filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return _itemFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView listItemRequestDate, listItemRequestTime, listItemRequestTypeExpense, listItemRequestAmount;

        public ViewHolder(View itemView) {
            super(itemView);

            listItemRequestDate = itemView.findViewById(R.id.listItemRequestDate);
            listItemRequestTime = itemView.findViewById(R.id.listItemRequestTime);
            listItemRequestAmount = itemView.findViewById(R.id.listItemRequestAmount);
            listItemRequestTypeExpense = itemView.findViewById(R.id.listItemRequestTypeExpense);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final ArrayList<Expense> list = _originalList;
            final ArrayList<Expense> nlist = new ArrayList<>(list.size());

            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            for (Expense request : list) {
                String filterableString = request.toString();

                if (filterableString.toLowerCase().contains(filterString))
                    nlist.add(request);
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _filteredList = (ArrayList<Expense>) results.values;
            notifyDataSetChanged();
        }
    }
}