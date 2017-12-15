package zw.co.tk.omnichannel.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */

@Database(entities = {Customer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();
}