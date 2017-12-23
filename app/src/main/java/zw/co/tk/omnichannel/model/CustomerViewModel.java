package zw.co.tk.omnichannel.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import zw.co.tk.omnichannel.OmniApplication;
import zw.co.tk.omnichannel.dao.CustomerDao;
import zw.co.tk.omnichannel.entity.Customer;

/**
 * Created by tdhla on 23-Dec-17.
 */

public class CustomerViewModel extends ViewModel {

    @Inject
    CustomerDao customerDao;

    private final LiveData<List<Customer>> customerList;
    private final LiveData<List<Customer>> customerToUploadList;

    public CustomerViewModel() {
        OmniApplication.appComponent.inject(CustomerViewModel.this);
        customerList = customerDao.getAllCustomers();
        customerToUploadList = customerDao.getAllCustomersToUpload();
    }

    public LiveData<List<Customer>> getCustomerList() {
        return customerList;
    }

    public LiveData<List<Customer>> getCustomerToUploadList() {
        return customerToUploadList;
    }
}
