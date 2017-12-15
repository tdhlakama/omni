package zw.co.tk.omnichannel;

import android.app.Application;

import zw.co.tk.omnichannel.dagger.component.AppComponent;
import zw.co.tk.omnichannel.dagger.component.DaggerAppComponent;
import zw.co.tk.omnichannel.dagger.module.AppModule;
import zw.co.tk.omnichannel.dagger.module.RoomModule;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class OmniApplication extends Application {

    public static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();

      appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }


}
