package zw.co.tk.omnichannel.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.activity.AccountDetailActivity;
import zw.co.tk.omnichannel.activity.PhotoActivity;
import zw.co.tk.omnichannel.activity.SignatureActivity;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.CustomerDocument;
import zw.co.tk.omnichannel.util.OmniUtil;

/**
 * Created by tdhla on 15-Dec-17.
 */
public class CustomerDocumentAdapter extends ArrayAdapter<CustomerDocument> {
    public CustomerDocumentAdapter(Context context, List<CustomerDocument> documents) {
        super(context, 0, documents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomerDocument document = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_document_activity, parent, false);
        }
        TextView documentType = convertView.findViewById(R.id.txt_documentType);
        documentType.setText((position + 1) + ". " + document.getDocumentType());

        Button btnUpload = convertView.findViewById(R.id.btnUpload);
        btnUpload.setText(document.getPath() != null ? "Saved" : "Upload");

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (document.getPath() == null) {
                    Intent intent = null;
                    if (document.getDocumentType().equals(OmniUtil.IMAGE)
                            || document.getDocumentType().equals(OmniUtil.COPY_ID)
                            || document.getDocumentType().equals(OmniUtil.PROOF_OF_RESIDENCE)) {
                        intent = new Intent(view.getContext(), PhotoActivity.class);
                    } else {
                        intent = new Intent(view.getContext(), SignatureActivity.class);
                    }
                    intent.putExtra("customerId", document.getCustomerId());
                    intent.putExtra("documentType", document.getDocumentType());
                    getContext().startActivity(intent);
                    Activity activity = (Activity) view.getContext();
                    activity.finish();
                } else {
                    Toast.makeText(getContext(), document.getDocumentType() + " Saved Already ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (document.getPath() != null) {
            btnUpload.setBackgroundResource(R.drawable.round_button_secondary);
        }

        return convertView;
    }

    @Nullable
    @Override
    public CustomerDocument getItem(int position) {
        return super.getItem(position);
    }
}
