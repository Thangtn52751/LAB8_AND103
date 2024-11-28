package dev.md19303.lab8;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.md19303.lab8.Model.Cake;
import dev.md19303.lab8.Service.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetail extends AppCompatActivity {

    private TextView titleAddressTxt, priceTxt, descriptionTxt;
    private ImageView picDetail, backBtn;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo các view
        titleAddressTxt = findViewById(R.id.titleAddressTxt);
        priceTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picDetail = findViewById(R.id.picDetail);
        backBtn = findViewById(R.id.backBtn);

        // Đặt ảnh cứng
        picDetail.setImageResource(R.drawable.cake);

        // Cấu hình Retrofit
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.DOMAIN) // URL cơ sở
                    .addConverterFactory(GsonConverterFactory.create()) // Bộ chuyển đổi JSON
                    .build();

            apiService = retrofit.create(APIService.class);
        } catch (Exception e) {
            Log.e("ProductDetail", "Lỗi khi khởi tạo Retrofit: " + e.getMessage());
            Toast.makeText(this, "Lỗi cấu hình API", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Lấy ID bánh từ Intent
        String cakeId = getIntent().getStringExtra("cake_id");
        if (cakeId != null) {
            fetchCakeDetails(cakeId);
        } else {
            Log.e("ProductDetail", "Không tìm thấy Cake ID trong Intent.");
            Toast.makeText(this, "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Xử lý nút quay lại
        backBtn.setOnClickListener(view -> finish());
    }

    private void fetchCakeDetails(String cakeId) {
        apiService.getCakeDetails(cakeId).enqueue(new Callback<Cake>() {
            @Override
            public void onResponse(Call<Cake> call, Response<Cake> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                } else {
                    Log.e("ProductDetail", "Không thể lấy thông tin bánh: " + response.message());
                    Toast.makeText(ProductDetail.this, "Không tìm thấy thông tin bánh!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cake> call, Throwable t) {
                Log.e("ProductDetail", "Lỗi khi gọi API: " + t.getMessage());
                Toast.makeText(ProductDetail.this, "Lỗi kết nối máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Cake cake) {
        titleAddressTxt.setText(cake.getName());
        priceTxt.setText("$" + cake.getPrice());
        descriptionTxt.setText(cake.getDescription() != null ? cake.getDescription() : "Không có mô tả.");
    }
}
