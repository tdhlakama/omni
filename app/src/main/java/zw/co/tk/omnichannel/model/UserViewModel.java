package zw.co.tk.omnichannel.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.dao.UserDao;
import zw.co.tk.omnichannel.entity.User;

/**
 * Created by tdhla on 25-Dec-17.
 */

public class UserViewModel extends ViewModel {

    @Inject
    UserDao userDao;

    private final LiveData<User> user;

    public UserViewModel() {
        OmniApplication.appComponent.inject(UserViewModel.this);
        user  = userDao.getUser();
    }

    public LiveData<User> getUser() {
        return user;
    }
}
