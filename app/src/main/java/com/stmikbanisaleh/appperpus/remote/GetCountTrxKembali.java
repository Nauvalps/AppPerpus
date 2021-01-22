package com.stmikbanisaleh.appperpus.remote;

public class GetCountTrxKembali {
    private int totalTrxKembali;

    public GetCountTrxKembali() {
    }

    public GetCountTrxKembali(int totalTrxKembali) {
        this.totalTrxKembali = totalTrxKembali;
    }

    public int getTotalTrxKembali() {
        return totalTrxKembali;
    }

    public void setTotalTrxKembali(int totalTrxKembali) {
        this.totalTrxKembali = totalTrxKembali;
    }
}
