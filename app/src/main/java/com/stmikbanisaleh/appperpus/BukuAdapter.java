package com.stmikbanisaleh.appperpus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuviewHolder> implements Filterable {
    private static String BASE_URL = "http://192.168.43.184:5000/";
    private Context context;
    private List<Buku> list = new ArrayList<>();
    private List<Buku> listSelected = new ArrayList<>();
    LayoutInflater layoutInflater;

    public BukuAdapter(Context context,List<Buku> list) {
        this.context = context;
        this.list = list;
        this.listSelected = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BukuviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
//        BukuviewHolder bukuviewHolder = new BukuviewHolder(view);
//        return bukuviewHolder;
        return new BukuviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuviewHolder holder, final int position) {
        final Buku buku = listSelected.get(position);
        holder.judul_buku.setText(buku.getNama_buku());
        holder.kategori.setText(buku.getKategori_buku());
       holder.stok.setText(Integer.toString(buku.getStok_buku()));
        Picasso.get().load(BASE_URL + buku.getGambar_buku()).resize(200,200).centerCrop()
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BukuActivity.class);
                intent.putExtra("ID", list.get(position).getId());
                intent.putExtra("Judul", list.get(position).getNama_buku());
                intent.putExtra("Kategori", list.get(position).getKategori_buku());
                intent.putExtra("Penerbit", list.get(position).getPenerbit());
                intent.putExtra("Gambar", list.get(position).getGambar_buku());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSelected.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Buku> filterList = new ArrayList<>();
                String search = constraint.toString();
                for (Buku buku : list) {
                    if (buku.getNama_buku().toLowerCase().contains(search.toLowerCase())) {
                        filterList.add(buku);
                    }
                }
                listSelected = filterList;
                FilterResults results = new FilterResults();
                results.values = filterList;
                    return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listSelected = (List<Buku>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class BukuviewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView judul_buku,kategori,stok;

        public BukuviewHolder(@NonNull View itemView) {
            super(itemView);
            judul_buku = itemView.findViewById(R.id.judul_buku);
            kategori = itemView.findViewById(R.id.kategori);
            cardView = itemView.findViewById(R.id.card_id);
            imageView = itemView.findViewById(R.id.gambar_buku);
            stok = itemView.findViewById(R.id.stok);
        }
    }
    public void addBuku(List<Buku> buk) {
        for (Buku bu : buk) {
            list.add(bu);
        }
        notifyDataSetChanged();
    }
}
