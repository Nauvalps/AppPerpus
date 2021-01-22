package com.stmikbanisaleh.appperpus;

public class Transaksi {
    private String buku_id,tgl_pinjam,tgl_kembali,user_id,status;

    public Transaksi() {
    }

    public Transaksi(String buku_id, String tgl_pinjam, String tgl_kembali, String user_id, String status) {
        this.buku_id = buku_id;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
        this.user_id = user_id;
        this.status = status;
    }

    public String getBuku_id() {
        return buku_id;
    }

    public void setBuku_id(String buku_id) {
        this.buku_id = buku_id;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
