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

public class UploadListActivity extends MenuBar {

    ListView customerListView;

    @Inject
    CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_list_activity);

        OmniApplication.appComponent.inject(UploadListActivity.this);

        customerListView =findViewById(R.id.list);
        List<Customer> customers = customerDao.getAllCustomerToUpload();
        CustomerAdapter adapter = new CustomerAdapter(this, customers);
        customerListView.setAdapter(adapter);

    }
}