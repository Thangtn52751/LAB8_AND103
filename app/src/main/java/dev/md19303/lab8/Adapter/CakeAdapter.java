package dev.md19303.lab8.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.md19303.lab8.LocationActivity;
import dev.md19303.lab8.Model.Cake;
import dev.md19303.lab8.PaymentActivity;
import dev.md19303.lab8.ProductDetail;
import dev.md19303.lab8.R;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {
    private Context context;
    private List<Cake> cakeList;
    private OnCakeInteractionListener listener;

    public CakeAdapter(List<Cake> cakeList, Context context, OnCakeInteractionListener listener) {
        this.cakeList = cakeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CakeAdapter.CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cake, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeAdapter.CakeViewHolder holder, int position) {
        Cake cake = cakeList.get(position);
        holder.tvProductName.setText(cake.getName());
        holder.tvProductPrice.setText(String.valueOf(cake.getPrice()));

        // Nút thêm sản phẩm
        holder.btnAddProduct.setOnClickListener(view -> {
            Intent intent = new Intent(context, LocationActivity.class);
            intent.putExtra("cake_id", cake.get_id()); // Truyền ID của sản phẩm để xử lý
            context.startActivity(intent);
        });

        // Xem chi tiết sản phẩm
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductDetail.class);
            intent.putExtra("cake_id", cake.get_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cakeList != null ? cakeList.size() : 0;
    }

    public class CakeViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnAddProduct;
        TextView tvProductName, tvProductPrice;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAddProduct = itemView.findViewById(R.id.btnAddProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }

    public interface OnCakeInteractionListener {
        void onEditCake(int position);

        void onDeleteCake(int position);
    }

    // Cập nhật danh sách trong adapter
    public void updateList(List<Cake> newList) {
        cakeList.clear();
        cakeList.addAll(newList);
        notifyDataSetChanged();
    }
}
