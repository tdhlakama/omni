package zw.co.tk.omnichannel.acitvity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import zw.co.tk.omnichannel.R;

/**
 * Created by tdhla on 15-Dec-17.
 */

public class MenuBar extends AppCompatActivity {

    public Toolbar toolbar;

    public void createToolBar(String title){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }


}
