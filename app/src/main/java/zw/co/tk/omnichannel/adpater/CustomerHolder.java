package zw.co.tk.omnichannel.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import zw.co.tk.omnichannel.R;

/**
 * Created by tdhla on 23-Dec-17.
 */

public class CustomerHolder extends RecyclerView.ViewHolder {

    TextView txt_first_name;
    TextView txt_surname;

    public CustomerHolder(View view) {
        super(view);
        txt_first_name = view.findViewById(R.id.et_first_name);
        txt_surname = view.findViewById(R.id.et_surname);
    }

}

