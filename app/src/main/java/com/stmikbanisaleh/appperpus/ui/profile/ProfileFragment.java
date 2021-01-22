package com.stmikbanisaleh.appperpus.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.stmikbanisaleh.appperpus.MainActivity;
import com.stmikbanisaleh.appperpus.R;
import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.GetCountTrxKembali;
import com.stmikbanisaleh.appperpus.remote.GetCountTrxPinjam;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private ImageButton imageButton;
    private TextView textView, textView1, textView2,textView3;
    private SharedPreferences sharedPreferences;
    private String getUserId;
    private Api api;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.text_nama);
        textView1 = view.findViewById(R.id.text_email);
        textView2 = view.findViewById(R.id.countPinjam);
        textView3 = view.findViewById(R.id.countKembali);
        imageButton = view.findViewById(R.id.btnSetting);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String nama = sharedPreferences.getString("GET_NAMA", "missing");
        String email = sharedPreferences.getString("GET_EMAIL", "missing");
        int id = sharedPreferences.getInt("GET_ID", 0);
        int getCountPinjam = sharedPreferences.getInt("GET_PINJAM",0);
        int getCountKembali = sharedPreferences.getInt("GET_KEMBALI",0);
        getUserId = Integer.toString(id);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button klik setting", Toast.LENGTH_LONG).show();
            }
        });
        textView.setText(nama);
        textView1.setText(email);
        textView2.setText(Integer.toString(getCountPinjam));
        textView3.setText(Integer.toString(getCountKembali));
        api = RetrofitClient.getInstance().getApi();
        getDataPinjam();
        getDataKembali();
    }

    private void getDataPinjam() {
        api.getTrxPinjam(getUserId).enqueue(new Callback<GetCountTrxPinjam>() {
            @Override
            public void onResponse(Call<GetCountTrxPinjam> call, Response<GetCountTrxPinjam> response) {
                if (response.code() == 200) {
                    int getPinjam = response.body().getTotalTrxPinjam();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("GET_PINJAM", getPinjam);
                    editor.apply();
                }else {
                    Toast.makeText(getContext(), "Error request gagal!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetCountTrxPinjam> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDataKembali() {
        api.getTrxKembali(getUserId).enqueue(new Callback<GetCountTrxKembali>() {
            @Override
            public void onResponse(Call<GetCountTrxKembali> call, Response<GetCountTrxKembali> response) {
                if (response.code() == 200) {
                    int getKembali = response.body().getTotalTrxKembali();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("GET_KEMBALI", getKembali);
                    editor.apply();
                }else {
                    Toast.makeText(getContext(), "Error request gagal!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetCountTrxKembali> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

//    private void btnOnClick(View view) {
//        Toast.makeText(getContext(), "Tombol di klik", Toast.LENGTH_SHORT).show();
//    }
}
