package zw.co.tk.omnichannel.network;

import java.util.List;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.http.GET;
import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */


public interface CustomerService {

    @GET("/customers")
    Call<List<Customer>> getCustomers();
}
