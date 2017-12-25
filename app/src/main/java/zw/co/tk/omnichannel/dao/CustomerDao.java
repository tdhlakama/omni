package zw.co.tk.omnichannel.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zw.co.tk.omnichannel.entity.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer")
    LiveData<List<Customer>> getAllCustomers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customer WHERE account_number is null")
    LiveData<List<Customer>> getAllCustomersToUpload();

    @Query("SELECT * FROM customer WHERE uid=:uid")
    Customer getCustomer(int uid);

    @Query("SELECT count(*) FROM customer")
    LiveData<Long> countAll();

    @Query("SELECT count(*) FROM customer WHERE account_number is null")
    LiveData<Long> countCustomersToSync();


}
