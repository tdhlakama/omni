package zw.co.tk.omnichannel.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.adpater.CustomerAdapter;
import zw.co.tk.omnichannel.model.CustomerViewModel;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.entity.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class AccountListActivity extends AppCompatActivity {

    @Inject
    CustomerDao customerDao;

    private CustomerViewModel customerViewModel;
    private CustomerAdapter customerAdapter;
    private RecyclerView recyclerView;
    private boolean upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list_activity);

        OmniApplication.appComponent.inject(AccountListActivity.this);

        upload = getIntent().getBooleanExtra("upload", false);

        recyclerView = findViewById(R.id.list);
        customerAdapter = new CustomerAdapter(new ArrayList<Customer>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

        if (upload) {
            customerViewModel.getCustomerToUploadList().observe(AccountListActivity.this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable List<Customer> list) {
                    customerAdapter.addItems(list);
                }
            });
        } else {
            customerViewModel.getCustomerList().observe(AccountListActivity.this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable List<Customer> list) {
                    customerAdapter.addItems(list);
                }
            });
        }
        Log.v("TAG","---------------------------------------------------" + customerAdapter.getItemCount());
        recyclerView.setAdapter(customerAdapter);
    }

}