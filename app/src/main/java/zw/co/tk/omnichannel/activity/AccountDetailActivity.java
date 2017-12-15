package zw.co.tk.omnichannel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.CustomerDocument;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class AccountDetailActivity extends MenuBar implements View.OnClickListener{

    Button btn_upload_image;
    Button btn_upload_copy_of_id;
    Button btn_upload_proof_of_residence;
    Customer customer;
    TextView txt_firstName,txt_surname,txt_address,txt_phone_number,txt_email_adress,txt_card_number;

    @Inject
    CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail_activity);

        OmniApplication.appComponent.inject(AccountDetailActivity.this);

        int customerId = getIntent().getIntExtra("customerId", 0);
        customer = customerDao.getCustomer(customerId);

        txt_firstName = findViewById(R.id.txt_first_name);
        txt_surname = findViewById(R.id.txt_surname);
        txt_address = findViewById(R.id.txt_address);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_email_adress = findViewById(R.id.txt_email_adress);
        txt_card_number = findViewById(R.id.txt_card_number);

        txt_firstName.setText(customer.getFirstName());
        txt_surname.setText(customer.getSurname());
        txt_address.setText(customer.getAddress());
        txt_phone_number.setText(customer.getPhoneNumber());
        txt_email_adress.setText(customer.getEmailAddress());
        txt_card_number.setText(customer.getCardNumber());

        btn_upload_image =findViewById(R.id.btn_upload_image);
        btn_upload_copy_of_id =findViewById(R.id.btn_upload_copy_of_id);
        btn_upload_proof_of_residence =findViewById(R.id.btn_upload_proof_of_residence);

        btn_upload_image.setOnClickListener(this);
        btn_upload_copy_of_id.setOnClickListener(this);
        btn_upload_proof_of_residence.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == btn_upload_image.getId())        {

            Intent intent = new Intent(AccountDetailActivity.this, SignatureActivity.class);
            intent.putExtra("customerId", customer.getUid());
            intent.putExtra("documentType", CustomerDocument.SIGNATURE);
            startActivity(intent);
        }

        if(view.getId() == btn_upload_copy_of_id.getId())        {

            Intent intent = new Intent(AccountDetailActivity.this, UploadDocumentActivity.class);
            intent.putExtra("customerId", customer.getUid());
            intent.putExtra("documentType", CustomerDocument.COPY_ID);
            startActivity(intent);
        }

        if(view.getId() == btn_upload_proof_of_residence.getId())        {

            Intent intent = new Intent(AccountDetailActivity.this, UploadDocumentActivity.class);
            intent.putExtra("customerId", customer.getUid());
            intent.putExtra("documentType", CustomerDocument.PROOF_OF_RESIDENCE);
            startActivity(intent);
        }

    }

}