package dev.md19303.lab8.Service;

import java.util.ArrayList;

import dev.md19303.lab8.Model.District;
import dev.md19303.lab8.Model.DistrictRequest;
import dev.md19303.lab8.Model.OrderRequest;
import dev.md19303.lab8.Model.OrderResponse;
import dev.md19303.lab8.Model.Province;
import dev.md19303.lab8.Model.ResponseGHN;
import dev.md19303.lab8.Model.Ward;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GHNServices {
    public static String GHN_URL = "https://dev-online-gateway.ghn.vn/";

    @GET("/shiip/public-api/master-data/province")
    Call<ResponseGHN<ArrayList<Province>>> getListProvince();

    @POST("/shiip/public-api/master-data/district")
    Call<ResponseGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("/shiip/public-api/master-data/ward")
    Call<ResponseGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);

    @POST("order/create") // Thay URL phù hợp
    Call<ResponseGHN<OrderResponse>> createOrder(@Body OrderRequest orderRequest);
}
