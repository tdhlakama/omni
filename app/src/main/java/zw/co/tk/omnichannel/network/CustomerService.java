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
import retrofit2.http.Path;
import retrofit2.http.Query;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.ServerResponse;

/**
 * Created by tdhla on 15-Dec-17.
 */
public interface CustomerService {


    @GET("test")
    Call<ServerResponse> getTestConnection();

    @GET("customers")
    Call<List<Customer>> getCustomers();

    @POST("customer/register")
    Call<ServerResponse> registerCustomer(@Body Customer customer);

    @GET("customers")
    Call<Customer> getCustomer(@Query("accountNumber") Long accountNumber);

    @Multipart
    @POST("customerDocument/upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name,
                                    @Query("accountNumber") Long accountNumber,
                                    @Query("documentType") String documentType);

}