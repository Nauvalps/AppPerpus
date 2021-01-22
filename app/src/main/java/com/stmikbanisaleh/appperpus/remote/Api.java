package com.stmikbanisaleh.appperpus.remote;

import com.stmikbanisaleh.appperpus.GetTransaksiWithUserId;
import com.stmikbanisaleh.appperpus.Transaksi;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("buku/")
    Call<PagingResponse> getData(@Query("page") int page, @Query("size") int size);
    @GET("transaksi/{user_id}")
    Call<List<GetTransaksiWithUserId>> getTransaksiUser(@Path("user_id") String user_id);
    @GET("trx-pinjam/{user_id}")
    Call<GetCountTrxPinjam> getTrxPinjam(@Path("user_id") String user_id);
    @GET("trx-kembali/{user_id}")
    Call<GetCountTrxKembali> getTrxKembali(@Path("user_id") String user_id);
    @POST("auth/signin")
    Call<LoginResponse> login (@Body AuthUser authUser);
    @POST("transaksi/")
    Call<Transaksi> postTransaksi(@Body Transaksi transaksi);
    @PUT("transaksi/{id_trx}")
    Call<TransactionResponse> updateStatusTrx(@Path("id_trx") int id_trx, @Body TransaksiUserRequest transaksiUserRequest );
}
