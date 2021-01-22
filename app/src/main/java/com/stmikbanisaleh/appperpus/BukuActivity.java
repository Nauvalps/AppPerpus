package com.stmikbanisaleh.appperpus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuActivity extends AppCompatActivity {
    private static String BASE_URL = "http://192.168.43.184:5000/";
    private SharedPreferences preferences;
    private GetTransaksiWithUserId getTransaksiWithUserId = null;
    private Api api;
    private String Judul, Kategori, Penerbit, Gambar;
    private int Id, getUserId;
    private TextView textId, textjudul, textkategori, penerbit, labelBukuId, labelUserId;
    private ImageView detail_gambar;
    private EditText textBukuId, textUserId, textBuku, textPinjam, textKembali;
    private Button btnSave, btnCancel;
    private AlertDialog alertDialog = null;
    private Buku buku = null;
    private View viewDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);
        setTitle("Detail Buku");
        textId = findViewById(R.id.textId);
        textjudul = findViewById(R.id.textjudul);
        textkategori = findViewById(R.id.textkategori);
        penerbit = findViewById(R.id.penerbit);
        detail_gambar = findViewById(R.id.detail_gambar);
        api = RetrofitClient.getInstance().getApi();
        preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        getUserId = preferences.getInt("GET_ID", 0);
        Intent intent = getIntent();
        Id = intent.getExtras().getInt("ID");
        Judul = intent.getExtras().getString("Judul");
        Kategori = intent.getExtras().getString("Kategori");
        Penerbit = intent.getExtras().getString("Penerbit");
        Gambar = intent.getExtras().getString("Gambar");
        textId.setText(String.valueOf(Id));
        textjudul.setText(Judul);
        textkategori.setText(Kategori);
        penerbit.setText(Penerbit);
        Picasso.get().load(BASE_URL + Gambar)
                .into(detail_gambar);
        setupDialog();
    }

    private void setupDialog() {
        viewDialog = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        labelBukuId = viewDialog.findViewById(R.id.labelBukuId);
        textBukuId = viewDialog.findViewById(R.id.editTextBukuId);
        labelUserId = viewDialog.findViewById(R.id.labelUserId);
        textUserId = viewDialog.findViewById(R.id.editTextUserId);
        textBuku = viewDialog.findViewById(R.id.editTextBuku);
        textPinjam = viewDialog.findViewById(R.id.editTextPinjam);
        textKembali = viewDialog.findViewById(R.id.editTextKembali);
        btnSave = viewDialog.findViewById(R.id.btnSave);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        textBuku.setEnabled(false);
        textBukuId.setEnabled(false);
        textUserId.setEnabled(false);
        textBukuId.setVisibility(View.GONE);
        labelBukuId.setVisibility(View.GONE);
        textUserId.setVisibility(View.GONE);
        labelUserId.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pinjam buku");
        builder.setView(viewDialog);
        alertDialog = builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                textPinjam.setText("");
                textKembali.setText("");
                textPinjam.requestFocus();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPinjam();
            }
        });
    }

    private void showDialog() {
        textUserId.setText(String.valueOf(getUserId));
        textBukuId.setText(String.valueOf(Id));
        textBuku.setText(Judul);
        alertDialog.show();
    }

    private void processPinjam() {
        try {
            if (textPinjam.getText().toString().isEmpty() && textKembali.getText().toString().isEmpty()) {
                Toast.makeText(this, "Tgl pinjam dan tgl kembali wajib di isi !", Toast.LENGTH_LONG).show();
            } else if (textPinjam.getText().toString().isEmpty()) {
                Toast.makeText(this, "Tgl pinjam wajib di isi !", Toast.LENGTH_LONG).show();
            } else if (textKembali.getText().toString().isEmpty()) {
                Toast.makeText(this, "Tgl kembali wajib di isi !", Toast.LENGTH_LONG).show();
            } else {
                String getTglPinjam = textPinjam.getText().toString();
                String getTglKembali = textKembali.getText().toString();
                String getBukuId = textBukuId.getText().toString();
                String getUserId = textUserId.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String convertTglPinjam = simpleDateFormat1.format(simpleDateFormat.parse(getTglPinjam));
                String convertTglKembali = simpleDateFormat1.format(simpleDateFormat.parse(getTglKembali));
                String status = "Pinjam";
                Transaksi transaksi = new Transaksi(getBukuId, convertTglPinjam, convertTglKembali, getUserId, status);
                api.postTransaksi(transaksi).enqueue(new Callback<Transaksi>() {
                    @Override
                    public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                        if (response.code() == 201) {
                            alertDialog.dismiss();
                            Toast.makeText(BukuActivity.this, "Peminjaman berhasil !", Toast.LENGTH_LONG).show();
                        }else if (response.code() == 409) {
                            Toast.makeText(BukuActivity.this, "Kamu hanya boleh meminjam 1 buku ini !", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(BukuActivity.this, "Peminjaman gagal !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Transaksi> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        showDialog();
    }
}
