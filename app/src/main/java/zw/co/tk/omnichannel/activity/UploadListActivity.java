package zw.co.tk.omnichannel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

public class UploadListActivity extends AppCompatActivity {

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

        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Customer customer = (Customer) customerListView.getItemAtPosition(position);
                Intent intent = new Intent(UploadListActivity.this, AccountDetailActivity.class);
                intent.putExtra("customerId", customer.getUid());
                startActivity(intent);
                finish();
            }
        });
    }
}