package zw.co.tk.omnichannel.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zw.co.tk.omnichannel.entity.User;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user limit 1")
    LiveData<User> getUser();

}
