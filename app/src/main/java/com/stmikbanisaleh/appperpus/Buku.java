package com.stmikbanisaleh.appperpus;

public class Buku {
    private String gambar_buku;
    private int id;
    private String nama_buku;
    private String penerbit;
    private String kategori_buku;
    private int stok_buku;
    public Buku() {

    }

    public Buku(String gambar_buku, int id, String nama_buku, String penerbit, String kategori_buku, int stok_buku) {
        this.gambar_buku = gambar_buku;
        this.id = id;
        this.nama_buku = nama_buku;
        this.penerbit = penerbit;
        this.kategori_buku = kategori_buku;
        this.stok_buku = stok_buku;
    }

    public String getGambar_buku() {
        return gambar_buku;
    }

    public void setGambar_buku(String gambar_buku) {
        this.gambar_buku = gambar_buku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_buku() {
        return nama_buku;
    }

    public void setNama_buku(String nama_buku) {
        this.nama_buku = nama_buku;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getKategori_buku() {
        return kategori_buku;
    }

    public void setKategori_buku(String kategori_buku) {
        this.kategori_buku = kategori_buku;
    }

    public int getStok_buku() {
        return stok_buku;
    }

    public void setStok_buku(int stok_buku) {
        this.stok_buku = stok_buku;
    }
}
