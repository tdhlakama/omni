package zw.co.tk.omnichannel.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.UserDao;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class MenuBar extends AppCompatActivity {

    public Toolbar toolbar;
    @Inject
    UserDao userDao;

    public void createToolBar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void logout() {
        userDao.deleteAll();
    }

}
