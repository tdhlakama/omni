package zw.co.tk.omnichannel.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("/api/customer/upload")
    boolean uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

}
