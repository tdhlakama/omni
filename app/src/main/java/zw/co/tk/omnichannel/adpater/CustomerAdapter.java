package zw.co.tk.omnichannel.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {
    public CustomerAdapter(Context context, List<Customer> customers) {
        super(context, 0, customers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Customer customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_activity, parent, false);
        }
        TextView txt_first_name =  convertView.findViewById(R.id.et_first_name);
        TextView txt_surname =convertView.findViewById(R.id.et_surname);
        txt_first_name.setText(customer.getFirstName());
        txt_surname.setText(customer.getSurname());
        return convertView;
    }
}
