package zw.co.tk.omnichannel.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zw.co.tk.omnichannel.model.Customer;

/**
 * Created by tdhla on 15-Dec-17.
 */
public interface CustomerService {

    @GET("/api/customers")
    Call<List<Customer>> getCustomers();

    @POST("/api/customer/register")
    Customer registerCustomer(@Body Customer customer);

    @POST("/api/customer/register")
    boolean registerCustomers(@Body List<Customer> customers);
}
