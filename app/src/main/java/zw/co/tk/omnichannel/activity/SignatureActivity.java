package zw.co.tk.omnichannel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Retrofit;
import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.dao.CustomerDocumentDao;
import zw.co.tk.omnichannel.entity.Customer;
import zw.co.tk.omnichannel.entity.CustomerDocument;
import zw.co.tk.omnichannel.util.OmniUtil;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class SignatureActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    Customer customer;
    CustomerDocument customerDocument;
    ProgressDialog progressDialog;

    @Inject
    CustomerDao customerDao;
    @Inject
    CustomerDocumentDao customerDocumentDao;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature_activity);
        OmniUtil.verifyStoragePermissions(SignatureActivity.this);

        OmniApplication.appComponent.inject(SignatureActivity.this);

        final int customerId = getIntent().getIntExtra("customerId", 0);
        customer = customerDao.getCustomer(customerId);
        customerDocument = new CustomerDocument();
        customerDocument.setDocumentType(OmniUtil.SIGNATURE);
        customerDocument.setCustomerId(customerId);

        TextView signature_pad_description = findViewById(R.id.signature_pad_description);
        signature_pad_description.setText("Agree " + customer);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        mSignaturePad = findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                Log.d("Signature", "Start");
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton = findViewById(R.id.clear_button);
        mSaveButton = findViewById(R.id.save_button);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();

                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(SignatureActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                    goBack();
                } else {
                    Toast.makeText(SignatureActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SignatureActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(OmniUtil.getAlbumStorageDir("OmniApp"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            customerDocument.setPath(photo.getPath());
            if (customerDocument.getPath() != null) {
                customerDocumentDao.insert(customerDocument);
            } else {
                return false;
            }
            OmniUtil.saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        SignatureActivity.this.sendBroadcast(mediaScanIntent);
    }

    public void goBack() {
        Intent intent = new Intent(SignatureActivity.this, AccountDetailActivity.class);
        intent.putExtra("customerId", customer.getUid());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}


