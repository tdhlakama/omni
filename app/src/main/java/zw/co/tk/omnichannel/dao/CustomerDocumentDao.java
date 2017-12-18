package zw.co.tk.omnichannel.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.CustomerDocument;

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
    List<Customer> getCustomerDocuments(int id);

    @Query("SELECT * FROM customerdocument WHERE uid=:uid")
    CustomerDocument getCustomerDocument(int uid);

}