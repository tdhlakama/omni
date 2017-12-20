package zw.co.tk.omnichannel.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import zw.co.tk.omnichannel.adpater.CustomerDocumentAdapter;
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

    Button btn_upload_file;
    Customer customer;
    ProgressDialog progressDialog;

    ListView documentListView;

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

        documentListView = findViewById(R.id.list);

        CustomerDocumentAdapter adapter = new CustomerDocumentAdapter(AccountDetailActivity.this, getDocs());
        documentListView.setAdapter(adapter);

        txt_firstName.setText(customer.getFirstName());
        txt_surname.setText(customer.getSurname());
        txt_address.setText(customer.getAddress());
        txt_phone_number.setText(customer.getPhoneNumber());
        txt_email_adress.setText(customer.getEmailAddress());
        txt_card_number.setText(customer.getCardNumber());

        if(customer.getAccountNumber()!=null) {
            txt_account_number.setText("Account Number " + customer.getAccountNumber());
            txt_account_number.setBackgroundColor(R.style.AppTheme_SecondaryButton);
        }

        txt_account_number.setText(customer.getAccountNumber() != null ? customer.getAccountNumber().toString() : "");

        btn_upload_file = findViewById(R.id.btn_upload_file);

        btn_upload_file.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        boolean customerInfo = customer.getAccountNumber() == null;

        if (filesReadyForUpload()) {
            btn_upload_file.setText("Upload File");
        } else {
            btn_upload_file.setText("Upload Required Documents");
            btn_upload_file.setEnabled(false);
        }

        if (customerInfo && allFilesUploaded()) {
            btn_upload_file.setText("File Uploaded Successfully");
            btn_upload_file.setEnabled(false);
        }
    }

    public List<CustomerDocument> getDocs() {
        List<CustomerDocument> customerDocuments = new ArrayList<>();
        for (String type : OmniUtil.getDocumentsTypes()) {
            CustomerDocument document = get(type);
            if (document == null) {
                CustomerDocument d = new CustomerDocument();
                d.setCustomerId(customer.getUid());
                d.setDocumentType(type);
                customerDocuments.add(d);
            } else {
                customerDocuments.add(document);
            }
        }
        return customerDocuments;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btn_upload_file.getId())
            if (isNetworkAvailable())
                uploadCustomer();
    }

    private void uploadCustomer() {
        progressDialog.show();

        if (customer.getAccountNumber() == null) {

            Call<ServerResponse> call = retrofit.
                    create(CustomerService.class).registerCustomer(OmniUtil.getCredentials(), customer);

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse serverResponse = response.body();
                    customer.setAccountNumber(serverResponse.getId());
                    customerDao.insert(customer);

                    customer = customerDao.getCustomer(customer.getUid());

                    uploadDocuments();

                    txt_account_number.setText("Account Number " + customer.getAccountNumber());
                    txt_account_number.setBackgroundColor(R.style.AppTheme_SecondaryButton);

                    btn_upload_file.setText("File Uploaded - Account Number " + customer.getAccountNumber());
                    btn_upload_file.setEnabled(false);

                    Toast.makeText(AccountDetailActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("Account", "Error Account Number" + t.getMessage());
                }
            });
        }


    }

    public void uploadDocuments() {

        for (CustomerDocument item : customerDocumentDao.getCustomerDocuments(customer.getUid())) {
            try {
                if (customer.getAccountNumber() != null && item.getId() == null && item.getPath() != null)
                    uploadFile(item);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                continue;
            }

        }
    }

    private void uploadFile(final CustomerDocument newCustomerDocument) {

        if (newCustomerDocument != null) {
            File file = new File(newCustomerDocument.getPath());

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            Call<ServerResponse> call = retrofit.create(CustomerService.class)
                    .uploadFile(OmniUtil.getCredentials(),
                            fileToUpload, filename, customer.getAccountNumber(), newCustomerDocument.getDocumentType());

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse serverResponse = response.body();
                    if (serverResponse != null) {
                        try {
                            if (serverResponse.getId() != null && !serverResponse.getId().equals(0L)) {
                                CustomerDocument customerDocument = customerDocumentDao.getCustomerDocument(newCustomerDocument.getUid());
                                customerDocument.setId(serverResponse.getId());
                                customerDocumentDao.insert(customerDocument);
                            } else {
                                Log.v("Response", "Cannot save" + response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                }
            });
        }


    }

    private CustomerDocument get(String documentType) {
        return customerDocumentDao.getCustomerDocumentByCustomer(customer.getUid(), documentType);
    }

    public boolean filesReadyForUpload() {
        for (String type : OmniUtil.getDocumentsTypes()) {
            CustomerDocument document = get(type);
            if (document == null) {
                return false;
            }
        }
        return true;
    }

    public boolean allFilesUploaded() {
        for (String type : OmniUtil.getDocumentsTypes()) {
            CustomerDocument document = get(type);
            if (document != null && document.getId() == null) {
                return false;
            }
        }
        return true;
    }

}