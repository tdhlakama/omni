package zw.co.tk.omnichannel.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.network.CustomerService;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class AccountActivity extends MenuBar implements View.OnClickListener{

    EditText et_firstName,et_surname,et_address,et_phoneNumber,et_emailAdress,et_cardNumber,et_idNumber;
    Button btn_save;

    @Inject
    CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        OmniApplication.appComponent.inject(AccountActivity.this);

        et_firstName = findViewById(R.id.et_first_name);
        et_surname = findViewById(R.id.et_surname);
        et_address = findViewById(R.id.et_address);
        et_phoneNumber = findViewById(R.id.et_phone_number);
        et_emailAdress = findViewById(R.id.et_email_address);
        et_cardNumber = findViewById(R.id.et_card_number);
        et_idNumber = findViewById(R.id.et_id_number);
        btn_save =findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == btn_save.getId())        {
            Customer customer = new Customer();
            customer.setFirstName(et_firstName.getText().toString());
            customer.setSurname(et_surname.getText().toString());
            customer.setAddress(et_address.getText().toString());
            customer.setPhoneNumber(et_phoneNumber.getText().toString());
            customer.setEmailAddress(et_emailAdress.getText().toString());
            customer.setCardNumber(et_cardNumber.getText().toString());
            customer.setIdNumber(et_idNumber.getText().toString());

            long customerId = customerDao.insert(customer);

            Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
            intent.putExtra("customerId", Long.valueOf(customerId).intValue());
            startActivity(intent);
        }

    }

}