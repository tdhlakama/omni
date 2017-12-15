package zw.co.tk.omnichannel.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.CustomerDocument;

/**
 * Created by tdhla on 15-Dec-17.
 */

@Database(entities = {Customer.class,CustomerDocument.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract CustomerDocumentDao customerDocumentDao();
}