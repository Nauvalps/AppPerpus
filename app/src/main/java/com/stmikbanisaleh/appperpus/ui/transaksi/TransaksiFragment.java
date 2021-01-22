package com.stmikbanisaleh.appperpus.ui.transaksi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stmikbanisaleh.appperpus.BukuActivity;
import com.stmikbanisaleh.appperpus.GetTransaksiWithUserId;
import com.stmikbanisaleh.appperpus.R;
import com.stmikbanisaleh.appperpus.TransaksiUserAdapter;
import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiFragment extends Fragment {

    private TransaksiViewModel transaksiViewModel;
    private RecyclerView recyclerView = null;
    private List<GetTransaksiWithUserId> list = new ArrayList<>();
    private GetTransaksiWithUserId getTransaksiWithUserId = null;
    private TransaksiUserAdapter transaksiUserAdapter = null;
    private Api api = null;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView navView;
    private String convertId;
    private int id;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaksi, container, false);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("GET_ID", 0);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerTransaksi);
        linearLayoutManager = new LinearLayoutManager(getContext());
        convertId = String.valueOf(id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        navView = getActivity().findViewById(R.id.nav_view);
        api = RetrofitClient.getInstance().getApi();
        loadData();
    }

    private void loadData() {
       api.getTransaksiUser(convertId).enqueue(new Callback<List<GetTransaksiWithUserId>>() {
           @Override
           public void onResponse(Call<List<GetTransaksiWithUserId>> call, Response<List<GetTransaksiWithUserId>> response) {
               if (response.code() == 200) {
                   list = response.body();
                   transaksiUserAdapter = new TransaksiUserAdapter(getContext(), list);
                   recyclerView.setAdapter(transaksiUserAdapter);
               }
           }

           @Override
           public void onFailure(Call<List<GetTransaksiWithUserId>> call, Throwable t) {
                t.printStackTrace();
           }
       });
       recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               if (dy > 0) {
                   navView.setVisibility(View.GONE);
               }else {
                   navView.setVisibility(View.VISIBLE);
               }
           }
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
           }
       });
    }
}
