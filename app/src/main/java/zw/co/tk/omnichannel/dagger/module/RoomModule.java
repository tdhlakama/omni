package zw.co.tk.omnichannel.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zw.co.tk.omnichannel.dao.AppDatabase;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.dao.CustomerDocumentDao;
import zw.co.tk.omnichannel.dao.UserDao;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(Application mApplication) {
        appDatabase = Room.databaseBuilder(mApplication,
                AppDatabase.class, "omni")
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    CustomerDao providesCustomerDao(AppDatabase appDatabase) {
        return appDatabase.customerDao();
    }

    @Singleton
    @Provides
    CustomerDocumentDao providesCustomerDocument(AppDatabase appDatabase) {
        return appDatabase.customerDocumentDao();
    }

    @Singleton
    @Provides
    UserDao providesUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }


}
