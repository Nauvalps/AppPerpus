package com.stmikbanisaleh.appperpus.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stmikbanisaleh.appperpus.Buku;
import com.stmikbanisaleh.appperpus.BukuAdapter;
import com.stmikbanisaleh.appperpus.R;
import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.PagingResponse;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView= null;
    private List<Buku> list = new ArrayList<>();
    private BukuAdapter adapter = null;
    private Buku buku = null;
    private Api api = null;
    private ProgressBar progressBar;
    private int page = 0;
    private int size = 6;
    private GridLayoutManager gridLayoutManager;
    private BottomNavigationView navView;
    private boolean mLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threshold = 6;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        progressBar = view.findViewById(R.id.loading);
        navView = getActivity().findViewById(R.id.nav_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        api = RetrofitClient.getInstance().getApi();
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Cari buku");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void loadData() {
        api.getData(page, size).enqueue(new Callback<PagingResponse>() {
            @Override
            public void onResponse(Call<PagingResponse> call, Response<PagingResponse> response) {
                if (response.code()  == 200) {
                    list = response.body().getBuku();
                    adapter = new BukuAdapter(getContext(), list);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getContext(), "Error request gagal!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<PagingResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0 ){
                    if (mLoading) {
                        if (totalItemCount > previous_total) {
                            mLoading = false;
                            previous_total = totalItemCount;
                        }
                    }
                    if (!mLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + view_threshold)) {
                        page++;
                        getPagination();
                        mLoading = true;
                    }
                    navView.setVisibility(View.GONE);
                }else{
                    navView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    private void getPagination() {
        progressBar.setVisibility(View.VISIBLE);
        api.getData(page, size).enqueue(new Callback<PagingResponse>() {
            @Override
            public void onResponse(Call<PagingResponse> call, Response<PagingResponse> response) {
                if (response.code()  == 200) {
                    list = response.body().getBuku();
                    adapter.addBuku(list);
                }else {
                    Toast.makeText(getContext(), "Error request gagal!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PagingResponse> call, Throwable t) {
//                list = new ArrayList<>();
//                adapter = new BukuAdapter(getContext(), list);
//                recyclerView.setAdapter(adapter);
                t.printStackTrace();
            }
        });
    }
}
