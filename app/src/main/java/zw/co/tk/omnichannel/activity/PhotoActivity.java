package zw.co.tk.omnichannel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindorks.paracamera.Camera;

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
 * Created by tdhla on 19-Dec-17.
 */

public class PhotoActivity extends AppCompatActivity {

    @Inject
    CustomerDao customerDao;
    @Inject
    CustomerDocumentDao customerDocumentDao;

    @Inject
    Retrofit retrofit;

    private ImageView picFrame;
    private Camera camera;
    ProgressDialog progressDialog;
    Customer customer;
    CustomerDocument customerDocument;
    String documentType;
    Button btnUpload;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);

        OmniUtil.verifyStoragePermissions(PhotoActivity.this);

        OmniApplication.appComponent.inject(PhotoActivity.this);

        int customerId = getIntent().getIntExtra("customerId", 0);
        documentType = getIntent().getStringExtra("documentType");
        customer = customerDao.getCustomer(customerId);
        customerDocument = new CustomerDocument();
        customerDocument.setCustomerId(customerId);
        customerDocument.setDocumentType(documentType);

        picFrame = findViewById(R.id.picFrame);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        btnUpload = findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addJpgSignatureToGallery(camera.getCameraBitmap()))
                    goBack();
                else
                    return;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        camera = new Camera.Builder()
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            if (bitmap != null) {
                picFrame.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this.getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(OmniUtil.getAlbumStorageDir("OmniApp"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            customerDocument.setPath(photo.getPath());
            OmniUtil.saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            customerDocumentDao.insert(customerDocument);
            result = true;
            Toast.makeText(PhotoActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        PhotoActivity.this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }

    public void goBack() {
        Intent intent = new Intent(PhotoActivity.this, AccountDetailActivity.class);
        intent.putExtra("customerId", customer.getUid());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}