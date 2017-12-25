package zw.co.tk.omnichannel.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.R;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.dao.UserDao;
import zw.co.tk.omnichannel.entity.Customer;
import zw.co.tk.omnichannel.model.CustomerViewModel;

public class MainActivity extends AppCompatActivity {

    Button createAccountBtn;
    Button syncBtn;
    Button listAccountBtn;
    Button logoutBtn;
    private CustomerViewModel customerViewModel;

    @Inject
    UserDao userDao;

    @Inject
    CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OmniApplication.appComponent.inject(MainActivity.this);

        redirectToMain();

        syncBtn = findViewById(R.id.btn_sync);
        createAccountBtn = findViewById(R.id.btn_create_account);
        listAccountBtn = findViewById(R.id.btn_list_accounts);
        logoutBtn = findViewById(R.id.btn_logout);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        listAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountListActivity.class);
                startActivity(intent);
            }
        });

        customerViewModel.getCountAll().observe(MainActivity.this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long count) {
                listAccountBtn.setText("Available Accounts (" + count+ ")");
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountListActivity.class);
                intent.putExtra("upload", true);
                startActivity(intent);
            }
        });

        customerViewModel.getCountCustomersToSync().observe(MainActivity.this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long count) {
                syncBtn.setText("Files to Upload (" + count + ")");
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                redirectToMain();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void redirectToMain() {

        if (userDao.getAll().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void logout() {
        userDao.deleteAll();
    }
}
