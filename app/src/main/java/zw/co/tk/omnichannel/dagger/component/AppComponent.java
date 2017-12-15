package zw.co.tk.omnichannel.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import zw.co.tk.omnichannel.activity.AccountActivity;
import zw.co.tk.omnichannel.activity.AccountListActivity;
import zw.co.tk.omnichannel.activity.MainActivity;
import zw.co.tk.omnichannel.activity.SignatureActivity;
import zw.co.tk.omnichannel.activity.UploadListActivity;
import zw.co.tk.omnichannel.dagger.module.AppModule;
import zw.co.tk.omnichannel.dagger.module.NetModule;
import zw.co.tk.omnichannel.dagger.module.RoomModule;

/**
 * Created by tdhla on 15-Dec-17.
 */
@Singleton
@Component(modules = {AppModule.class, RoomModule.class, NetModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(AccountListActivity accountListActivity);

    void inject(UploadListActivity uploadListActivity);

    void inject(AccountActivity accountActivity);

    void inject(SignatureActivity signatureActivity);

}
