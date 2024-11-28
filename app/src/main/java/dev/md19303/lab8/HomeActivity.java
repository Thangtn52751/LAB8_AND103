package dev.md19303.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dev.md19303.lab8.Adapter.CakeAdapter;
import dev.md19303.lab8.Model.Cake;
import dev.md19303.lab8.Service.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements CakeAdapter.OnCakeInteractionListener {
    EditText search;
    RecyclerView rv_cake;
    FloatingActionButton fab_cart;
    private List<Cake> cakeList = new ArrayList<>();
    private APIService apiService;
    private CakeAdapter cakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search = findViewById(R.id.search);
        rv_cake = findViewById(R.id.rv_cake);
        fab_cart = findViewById(R.id.fab_cart);

        rv_cake.setLayoutManager(new GridLayoutManager(this, 2));
        cakeAdapter = new CakeAdapter(cakeList, this, this);
        rv_cake.setAdapter(cakeAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);

        fetchCakes();

        fab_cart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Automatically filter the list as the user types
                searchCakes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });
    }

    private void fetchCakes() {
        apiService.getCake().enqueue(new Callback<List<Cake>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cake>> call, @NonNull Response<List<Cake>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cakeList.clear();
                    cakeList.addAll(response.body());
                    cakeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to fetch cakes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cake>> call, @NonNull Throwable t) {
                Log.e("API ERROR", "Error fetching cakes: " + t.getMessage());
            }
        });
    }

    private void searchCakes(String query) {
        if (query.isEmpty()) {
            fetchCakes(); // Reset the list to show all cakes when the search is empty
            return;
        }

        apiService.searchCakes(query).enqueue(new Callback<List<Cake>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cake>> call, @NonNull Response<List<Cake>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cakeList.clear();
                    cakeList.addAll(response.body());
                    cakeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivity.this, "No cakes found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cake>> call, @NonNull Throwable t) {
                Log.e("API ERROR", "Error searching cakes: " + t.getMessage());
            }
        });
    }

    @Override
    public void onEditCake(int position) {

    }

    @Override
    public void onDeleteCake(int position) {

    }
}