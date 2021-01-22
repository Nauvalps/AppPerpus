package com.stmikbanisaleh.appperpus.remote;

public class LoginResponse {
    private int id;
    private String nama;
    private String email;
    private String password;
    private String accessToken;

    public LoginResponse() {
    }

    public LoginResponse(int id, String nama, String email, String password, String accessToken) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
