package margo.covid.detector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import margo.covid.detector.models.ListItem;
import margo.covid.detector.R;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private Context context;
    private ArrayList<ListItem> items;

    public ItemsAdapter(Context context, ArrayList<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ListItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.mobile.setText(item.getMobile());
        holder.address.setText(item.getAddress());
        holder.image.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView mobile;
        TextView address;
        ImageView image;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            address = itemView.findViewById(R.id.address);
            image = itemView.findViewById(R.id.image);
        }
    }
}
