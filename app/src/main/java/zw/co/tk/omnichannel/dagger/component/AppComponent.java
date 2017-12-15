package zw.co.tk.omnichannel.dagger.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import zw.co.tk.omnichannel.acitvity.AccountActivity;
import zw.co.tk.omnichannel.acitvity.AccountListActivity;
import zw.co.tk.omnichannel.acitvity.MainActivity;
import zw.co.tk.omnichannel.acitvity.UploadListActivity;
import zw.co.tk.omnichannel.dao.AppDatabase;
import zw.co.tk.omnichannel.dagger.module.AppModule;
import zw.co.tk.omnichannel.dagger.module.RoomModule;
import zw.co.tk.omnichannel.dao.CustomerDao;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(AccountListActivity accountListActivity);

    void inject(UploadListActivity uploadListActivity);

    void inject(AccountActivity accountActivity);

    CustomerDao customerDao();

    AppDatabase appDataBase();

    Application application();
}
