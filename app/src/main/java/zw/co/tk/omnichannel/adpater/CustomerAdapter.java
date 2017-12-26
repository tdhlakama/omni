package zw.co.tk.omnichannel.adpater;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.activity.AccountDetailActivity;
import zw.co.tk.omnichannel.entity.Customer;

/**
 * Created by tdhla on 23-Dec-17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

    private List<Customer> customerList;

    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override

    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_activity, parent, false));
    }


    @Override
    public void onBindViewHolder(final CustomerHolder holder, int position) {

        final Customer customer = customerList.get(position);
        holder.txt_first_name.setText("First Name - " +customer.getFirstName());
        holder.txt_surname.setText("Surname - " + customer.getSurname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AccountDetailActivity.class);
                intent.putExtra("customerId", customer.getUid());
                view.getContext().startActivity(intent);
                Activity activity = (Activity) view.getContext();
                activity.finish();
            }
        });

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