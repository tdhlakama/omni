package zw.co.tk.omnichannel.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zw.co.tk.omnichannel.model.Customer;
import zw.co.tk.omnichannel.model.ServerResponse;
import zw.co.tk.omnichannel.model.User;

/**
 * Created by tdhla on 15-Dec-17.
 */
public interface CustomerService {


    @GET("test")
    Call<ServerResponse> getTestConnection(@Header("Authorization") String authHeader);

    @GET("user")
    Call<User> getUser(@Header("Authorization") String authHeader, @Query("username") String username);

    @GET("customers")
    Call<List<Customer>> getCustomers(@Header("Authorization") String authHeader);

    @POST("customer/register")
    Call<ServerResponse> registerCustomer(@Header("Authorization") String authHeader, @Body Customer customer);

    @GET("customers")
    Call<Customer> getCustomer(@Header("Authorization") String authHeader, @Query("accountNumber") Long accountNumber);

    @Multipart
    @POST("customerDocument/upload")
    Call<ServerResponse> uploadFile(@Header("Authorization") String authHeader, @Part MultipartBody.Part file, @Part("file") RequestBody name,
                                    @Query("accountNumber") Long accountNumber,
                                    @Query("documentType") String documentType);

}