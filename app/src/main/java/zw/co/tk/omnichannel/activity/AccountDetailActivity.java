package zw.co.tk.omnichannel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.dao.CustomerDocumentDao;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.CustomerDocument;
import zw.co.tk.omnichannel.model.ServerResponse;
import zw.co.tk.omnichannel.network.CustomerService;
import zw.co.tk.omnichannel.util.OmniUtil;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class AccountDetailActivity extends MenuBar implements View.OnClickListener {

    Button btn_upload_image;
    Button btn_upload_signature;
    Button btn_upload_copy_of_id;
    Button btn_upload_proof_of_residence;
    Button btn_upload_file;
    Customer customer;
    ProgressDialog progressDialog;
    CustomerDocument customerDocument;

    TextView txt_firstName, txt_surname, txt_address, txt_phone_number, txt_email_adress, txt_card_number, txt_account_number;

    @Inject
    CustomerDao customerDao;
    @Inject
    CustomerDocumentDao customerDocumentDao;
    @Inject
    Retrofit retrofit;


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
        txt_account_number = findViewById(R.id.txt_account_number);

        txt_firstName.setText(customer.getFirstName());
        txt_surname.setText(customer.getSurname());
        txt_address.setText(customer.getAddress());
        txt_phone_number.setText(customer.getPhoneNumber());
        txt_email_adress.setText(customer.getEmailAddress());
        txt_card_number.setText(customer.getCardNumber());
        txt_account_number.setText(customer.getAccountNumber() != null ? customer.getAccountNumber().toString() : "");

        btn_upload_signature = findViewById(R.id.btn_upload_signature);
        btn_upload_image = findViewById(R.id.btn_upload_image);
        btn_upload_copy_of_id = findViewById(R.id.btn_upload_copy_of_id);
        btn_upload_proof_of_residence = findViewById(R.id.btn_upload_proof_of_residence);
        btn_upload_file = findViewById(R.id.btn_upload_file);

        btn_upload_image.setOnClickListener(this);
        btn_upload_copy_of_id.setOnClickListener(this);
        btn_upload_proof_of_residence.setOnClickListener(this);
        btn_upload_file.setOnClickListener(this);
        btn_upload_signature.setOnClickListener(this);

        boolean customerInfo = customer.getAccountNumber() == null;
        btn_upload_file.setVisibility(customerInfo ? View.VISIBLE : View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        if (customer.getAccountNumber() != null) {
            btn_upload_signature.setText("Account Detail Updated");
            btn_upload_signature.setEnabled(false);
        } else {
            btn_upload_signature.setText("Upload Account Details");
        }

        if (documentReadyForUpload(CustomerDocument.SIGNATURE)) {
            if (get(CustomerDocument.SIGNATURE).getId() != null) {
                btn_upload_image.setText("Submitted Image");
                btn_upload_image.setEnabled(false);
            } else {
                btn_upload_image.setText("Upload Image");
            }
        }

        if (documentReadyForUpload(CustomerDocument.SIGNATURE)) {
            if (get(CustomerDocument.SIGNATURE).getId() != null) {
                btn_upload_signature.setText("Submitted Signature");
                btn_upload_signature.setEnabled(false);
            } else {
                btn_upload_signature.setText("Upload Signature");
            }
        }


        if (documentReadyForUpload(CustomerDocument.COPY_ID)) {
            if (get(CustomerDocument.COPY_ID).getId() != null) {
                btn_upload_copy_of_id.setText("Submitted Copy Of ID");
                btn_upload_copy_of_id.setEnabled(false);
            } else {
                btn_upload_copy_of_id.setText("Upload Copy Of ID");
            }
        }

        if (documentReadyForUpload(CustomerDocument.PROOF_OF_RESIDENCE)) {
            if (get(CustomerDocument.PROOF_OF_RESIDENCE).getId() != null) {
                btn_upload_copy_of_id.setText("Submitted Proof of Residence");
                btn_upload_copy_of_id.setEnabled(false);
            } else {
                btn_upload_proof_of_residence.setText("Upload Proof of Residence");
            }
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == btn_upload_image.getId()) {
            if (documentReadyForUpload(CustomerDocument.SIGNATURE)) {
                customerDocument = get(CustomerDocument.SIGNATURE);
            } else {
                Intent intent = new Intent(AccountDetailActivity.this, SignatureActivity.class);
                intent.putExtra("customerId", customer.getUid());
                intent.putExtra("documentType", CustomerDocument.SIGNATURE);
                startActivity(intent);
            }
        }

        if (view.getId() == btn_upload_image.getId()) {

            if (documentReadyForUpload(CustomerDocument.IMAGE)) {
                customerDocument = get(CustomerDocument.IMAGE);
            } else {
                Intent intent = new Intent(AccountDetailActivity.this, PhotoActivity.class);
                intent.putExtra("customerId", customer.getUid());
                intent.putExtra("documentType", CustomerDocument.IMAGE);
                startActivity(intent);
                finish();
            }
        }

        if (view.getId() == btn_upload_copy_of_id.getId()) {

            if (documentReadyForUpload(CustomerDocument.COPY_ID)) {
                customerDocument = get(CustomerDocument.COPY_ID);
            } else {
                Intent intent = new Intent(AccountDetailActivity.this, PhotoActivity.class);
                intent.putExtra("customerId", customer.getUid());
                intent.putExtra("documentType", CustomerDocument.COPY_ID);
                startActivity(intent);
                finish();
            }
        }

        if (view.getId() == btn_upload_proof_of_residence.getId()) {

            if (documentReadyForUpload(CustomerDocument.PROOF_OF_RESIDENCE)) {
                customerDocument = get(CustomerDocument.PROOF_OF_RESIDENCE);
            } else {

                Intent intent = new Intent(AccountDetailActivity.this, PhotoActivity.class);
                intent.putExtra("customerId", customer.getUid());
                intent.putExtra("documentType", CustomerDocument.PROOF_OF_RESIDENCE);
                startActivity(intent);
            }
        }

        if (view.getId() == btn_upload_file.getId())
            if(isNetworkAvailable())
            uploadCustomer(customer);
    }

    Long customerAccountNumber = null;

    private void uploadCustomer(final Customer customer) {
        if (customer.getAccountNumber() == null ) {
            Call<ServerResponse> call = retrofit.
                    create(CustomerService.class).registerCustomer(OmniUtil.getCredentials(), customer);

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse serverResponse = response.body();
                    customer.setAccountNumber(serverResponse.getId());
                    customerAccountNumber = serverResponse.getId();
                    customerDao.insert(customer);

                    Log.e("Account", "New Account Number " + serverResponse.getId());
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("Account", "Error Account Number" + t.getMessage());

                }
            });
        } else
            customerAccountNumber = customer.getAccountNumber();

        for (CustomerDocument customerDocument : customerDocumentDao.getCustomerDocuments(customer.getUid())) {
            if (customerAccountNumber != null)
                uploadFile(customerDocument);
        }
    }

    private void uploadFile(CustomerDocument newCustomerDocument) {

        this.customerDocument = newCustomerDocument;

        progressDialog.show();

        File file = new File(customerDocument.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<ServerResponse> call = retrofit.create(CustomerService.class)
                .uploadFile(OmniUtil.getCredentials(),
                        fileToUpload, filename, customer.getAccountNumber(), customerDocument.getDocumentType());

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (!serverResponse.getId().equals(0L)) {
                        customerDocument.setId(serverResponse.getId());
                        customerDocumentDao.insert(customerDocument);
                    } else {
                        Log.v("Response", "Cannot save");
                    }
                }
                progressDialog.dismiss();
                customerDocument = null;
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                customerDocument = null;
                t.printStackTrace();
            }
        });

    }

    public boolean documentReadyForUpload(String documentType) {
        CustomerDocument document = get(documentType);
        if (document != null && document.getPath() != null)
            return true;
        else
            return false;
    }

    private CustomerDocument get(String documentType) {
        return customerDocumentDao.getCustomerDocumentByCustomer(customer.getUid(), documentType);
    }

}