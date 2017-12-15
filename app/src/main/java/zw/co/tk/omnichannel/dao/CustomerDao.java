package zw.co.tk.omnichannel.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer")
    List<Customer> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customer WHERE account_number is null or account_number=''")
    List<Customer> getAllCustomerToUpload();

    @Query("SELECT * FROM customer WHERE uid=:uid")
    Customer getCustomer(int uid);

}
