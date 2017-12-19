package zw.co.tk.omnichannel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class UploadDocumentActivity extends MenuBar {

    Button btnUpload, btnPickImage;
    String mediaPath;
    ImageView imgView;
    ProgressDialog progressDialog;
    Customer customer;
    CustomerDocument customerDocument;
    String documentType;

    @Inject
    CustomerDao customerDao;
    @Inject
    CustomerDocumentDao customerDocumentDao;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document_activity);
        OmniUtil.verifyStoragePermissions(UploadDocumentActivity.this);

        OmniApplication.appComponent.inject(UploadDocumentActivity.this);

        int customerId = getIntent().getIntExtra("customerId", 0);
        documentType = getIntent().getStringExtra("documentType");
        customer = customerDao.getCustomer(customerId);
        customerDocument = new CustomerDocument();
        customerDocument.setCustomerId(customerId);
        customerDocument.setDocumentType(documentType);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        btnUpload = (Button) findViewById(R.id.upload);
        btnPickImage = (Button) findViewById(R.id.pick_img);
        imgView = (ImageView) findViewById(R.id.preview);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadFile() {
        progressDialog.show();

        File file = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<ServerResponse> call = retrofit.create(CustomerService.class)
                .uploadFile(OmniUtil.getCredentials(),
                        fileToUpload, filename, customer.getAccountNumber(), documentType);

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
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}