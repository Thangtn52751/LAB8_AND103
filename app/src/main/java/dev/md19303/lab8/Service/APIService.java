package dev.md19303.lab8.Service;

import java.util.List;

import dev.md19303.lab8.Model.Cake;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String DOMAIN = "http://192.168.1.8:3000"; // Địa chỉ server nội bộ

    // Endpoint for Cake
    @GET("/")
    Call<List<Cake>> getCake();

    @POST("/add_cake")
    @Headers("Content-Type: application/json")
    Call<Void> addCake(@Body Cake cake);

    @GET("/delete/{id}")
    Call<Void> deleteCake(@Path("id") String id);

    @PUT("/update/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> updateCake(@Path("id") String id, @Body Cake updatedCake);

    @GET("/search")
    Call<List<Cake>> searchCakes(@Query("name") String name);

    @GET("/product/{id}")
    Call<Cake> getCakeDetails(@Path("id") String id);


}
