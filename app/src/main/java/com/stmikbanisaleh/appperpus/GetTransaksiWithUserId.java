package com.stmikbanisaleh.appperpus;


public class GetTransaksiWithUserId {
    private int id_trx;
    private String buku_id;
    private String nama_buku;
    private String kategori_buku;
    private String gambar_buku;
    private String tgl_pinjam;
    private String tgl_kembali;
    private String user_id;
    private String status;

    public GetTransaksiWithUserId() {
    }

    public GetTransaksiWithUserId(int id_trx, String buku_id, String nama_buku, String kategori_buku, String gambar_buku, String tgl_pinjam, String tgl_kembali, String status) {
        this.id_trx = id_trx;
        this.buku_id = buku_id;
        this.nama_buku = nama_buku;
        this.kategori_buku = kategori_buku;
        this.gambar_buku = gambar_buku;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
        this.status = status;
    }

    public int getId_trx() {
        return id_trx;
    }

    public void setId_trx(int id_trx) {
        this.id_trx = id_trx;
    }

    public String getBuku_id() {
        return buku_id;
    }

    public void setBuku_id(String buku_id) {
        this.buku_id = buku_id;
    }


    public String getNama_buku() {
        return nama_buku;
    }

    public void setNama_buku(String nama_buku) {
        this.nama_buku = nama_buku;
    }

    public String getKategori_buku() {
        return kategori_buku;
    }

    public void setKategori_buku(String kategori_buku) {
        this.kategori_buku = kategori_buku;
    }

    public String getGambar_buku() {
        return gambar_buku;
    }

    public void setGambar_buku(String gambar_buku) {
        this.gambar_buku = gambar_buku;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
