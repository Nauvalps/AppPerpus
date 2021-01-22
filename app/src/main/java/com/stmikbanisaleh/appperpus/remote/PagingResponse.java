package com.stmikbanisaleh.appperpus.remote;

import com.stmikbanisaleh.appperpus.Buku;

import java.util.List;

public class PagingResponse {
    private int totalItems;
    private List<Buku> buku;
    private int totalPages;
    private int currentPage;

    public  PagingResponse() {

    }

    public PagingResponse(int totalItems, List<Buku> buku, int totalPages, int currentPage) {
        this.totalItems = totalItems;
        this.buku = buku;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Buku> getBuku() {
        return buku;
    }

    public void setBuku(List<Buku> buku) {
        this.buku = buku;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
