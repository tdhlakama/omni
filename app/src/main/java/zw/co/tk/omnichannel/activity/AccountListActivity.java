package zw.co.tk.omnichannel.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.adpater.CustomerAdapter;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class AccountListActivity extends MenuBar {

    ListView customerListView;
    @Inject
    CustomerDao customerDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list_activity);

        OmniApplication.appComponent.inject(AccountListActivity.this);

        customerListView =findViewById(R.id.list);
        List<Customer> customes = customerDao.getAll();
        CustomerAdapter adapter = new CustomerAdapter(this, customes);
        customerListView.setAdapter(adapter);

    }
}