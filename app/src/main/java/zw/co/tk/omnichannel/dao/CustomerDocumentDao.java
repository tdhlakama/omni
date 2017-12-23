package zw.co.tk.omnichannel.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zw.co.tk.omnichannel.entity.CustomerDocument;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Dao
public interface CustomerDocumentDao {

    @Query("SELECT * FROM customerdocument")
    List<CustomerDocument> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CustomerDocument customerDocument);

    @Delete
    void delete(CustomerDocument customerDocument);

    @Query("SELECT * FROM customerdocument WHERE customer_id =:id")
    List<CustomerDocument> getCustomerDocuments(int id);

    @Query("SELECT * FROM customerdocument WHERE uid=:uid")
    CustomerDocument getCustomerDocument(int uid);

    @Query("SELECT * FROM customerdocument WHERE customer_id =:customerId and document_type=:documentType")
    CustomerDocument getCustomerDocumentByCustomer(int customerId, String documentType);

}
