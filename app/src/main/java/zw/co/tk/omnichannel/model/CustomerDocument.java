package zw.co.tk.omnichannel.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Entity
public class CustomerDocument {

    public static String COPY_ID ="COPY OF ID";
    public static String PROOF_OF_RESIDENCE ="PROOF OF RESIDENCE";
    public static String SIGNATURE ="SIGNATURE";

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "document_type")
    private String documentType;

    @ColumnInfo(name = "path")
    private String path;

    @ColumnInfo(name = "document", typeAffinity = ColumnInfo.BLOB)
    private byte[] document;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
