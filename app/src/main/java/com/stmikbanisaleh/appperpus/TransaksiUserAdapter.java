package com.stmikbanisaleh.appperpus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;
import com.stmikbanisaleh.appperpus.remote.TransactionResponse;
import com.stmikbanisaleh.appperpus.remote.TransaksiUserRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransaksiUserAdapter extends RecyclerView.Adapter<TransaksiUserAdapter.TransaksiviewHolder> {
    private static String BASE_URL = "http://192.168.43.184:5000/img-buku/";
    private Context context;
    private List<GetTransaksiWithUserId> list = new ArrayList<>();
    private String status = "Kembali";
    LayoutInflater inflater;

    public TransaksiUserAdapter(Context context, List<GetTransaksiWithUserId> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransaksiUserAdapter.TransaksiviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransaksiUserAdapter.TransaksiviewHolder holder, final int position) {
        final GetTransaksiWithUserId getTransaksiWithUserId = list.get(position);
        String getTglPinjam = getTransaksiWithUserId.getTgl_pinjam();
        String getTglKembali = getTransaksiWithUserId.getTgl_kembali();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateTglPinjam = myFormat.parse(getTglPinjam);
            Date dateTglKembali = myFormat.parse(getTglKembali);
            long diff = dateTglKembali.getTime() - dateTglPinjam.getTime();
            CountDownTimer cdt = new CountDownTimer(diff, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                    holder.labelDate.setText("Sisa Waktu : "+days+ " Hari");
                }

                @Override
                public void onFinish() {
                    holder.labelDate.setText("Waktu peminjaman selesai");
                }
            };
            cdt.start();
            holder.labelIdTrx.setText(String.valueOf(getTransaksiWithUserId.getId_trx()));
            holder.labelIdTrx.setVisibility(View.GONE);
            holder.labelJudul.setText(getTransaksiWithUserId.getNama_buku());
            holder.labelStatus.setText(getTransaksiWithUserId.getStatus());
            if (getTransaksiWithUserId.getStatus().equals("Kembali")) {
                holder.labelDate.setVisibility(View.GONE);
                holder.labelStatus.setBackgroundColor(Color.parseColor("#00e676"));
                holder.button.setVisibility(View.GONE);
            }
            Picasso.get().load(BASE_URL + getTransaksiWithUserId.getGambar_buku()).into(holder.imageView);
            holder.position = position;
            holder.getTrx = getTransaksiWithUserId;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TransaksiviewHolder extends RecyclerView.ViewHolder {
        Api api;
        CardView cardView;
        ImageView imageView;
        Button button;
        int position;
        GetTransaksiWithUserId getTrx;
        TextView labelJudul, labelStatus, labelDate, labelIdTrx;

        public TransaksiviewHolder(@NonNull View itemView) {
            super(itemView);
            api = RetrofitClient.getInstance().getApi();
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.gambarBuku);
            labelIdTrx = itemView.findViewById(R.id.labelId);
            labelDate = itemView.findViewById(R.id.labelDate);
            labelJudul = itemView.findViewById(R.id.labelJudul);
            labelStatus = itemView.findViewById(R.id.labelStatus);
            button = itemView.findViewById(R.id.btnKembali);
            final TransaksiUserRequest transaksiUserRequest = new TransaksiUserRequest(status);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Apakah anda ingin mengembalikan buku?");
                    builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            api.updateStatusTrx(getTrx.getId_trx(), transaksiUserRequest).enqueue(new Callback<TransactionResponse>() {
                                @Override
                                public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                                    if (response.code() == 200) {
                                        dialog.dismiss();
                                        labelDate.setVisibility(View.GONE);
                                        labelStatus.setBackgroundColor(Color.parseColor("#00e676"));
                                        button.setVisibility(View.GONE);
                                        Toast.makeText(v.getContext(), "Pengembalian berhasil !", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(v.getContext(), "Pengembalian gagal !", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<TransactionResponse> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }
}
