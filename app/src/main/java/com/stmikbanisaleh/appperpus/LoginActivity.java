package com.stmikbanisaleh.appperpus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stmikbanisaleh.appperpus.remote.Api;
import com.stmikbanisaleh.appperpus.remote.AuthUser;
import com.stmikbanisaleh.appperpus.remote.LoginResponse;
import com.stmikbanisaleh.appperpus.remote.RetrofitClient;
import com.stmikbanisaleh.appperpus.ui.profile.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Api api;
    private SharedPreferences sharedPreferences;
    private EditText inputEmail, inputPassword;
    private Bundle bundle;
    private ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        api = RetrofitClient.getInstance().getApi();
        profileFragment = new ProfileFragment();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("GET_TOKEN","");
        if (!token.equals("")) {
            RetrofitClient.setupToken(token);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void onClickLogin(View view) {
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        AuthUser authUser = new AuthUser(email, password);
        api.login(authUser).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
//                    response.body();
                    String token = response.body().getAccessToken();
                    int id  = response.body().getId();
                    String nama = response.body().getNama();
                    String email = response.body().getEmail();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("GET_TOKEN", token);
                    editor.putString("GET_NAMA", nama);
                    editor.putString("GET_EMAIL", email);
                    editor.putInt("GET_ID",id);
                    editor.apply();
//                    String liatNama = sharedPreferences.getString("GET_NAMA", "missing");
                    RetrofitClient.setupToken(token);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Username atau password salah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}
