package zw.co.tk.omnichannel.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.entity.Customer;

/**
 * Created by tdhla on 23-Dec-17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

    private List<Customer> customerList;

    private View.OnLongClickListener longClickListener;

    public CustomerAdapter(List<Customer> customerList, View.OnLongClickListener longClickListener) {
        this.customerList = customerList;
        this.longClickListener = longClickListener;
    }

    @Override

    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_activity, parent, false));
    }


    @Override
    public void onBindViewHolder(final CustomerHolder holder, int position) {

        Customer customer = customerList.get(position);
        holder.txt_first_name.setText(customer.getFirstName());
        holder.txt_surname.setText(customer.getSurname());
        holder.itemView.setOnLongClickListener(longClickListener);

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void addItems(List<Customer> customerList) {
        this.customerList = customerList;
        notifyDataSetChanged();
    }



}