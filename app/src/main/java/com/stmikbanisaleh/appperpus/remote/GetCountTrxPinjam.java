package com.stmikbanisaleh.appperpus.remote;

public class GetCountTrxPinjam {
    private int totalTrxPinjam;

    public GetCountTrxPinjam() {
    }

    public GetCountTrxPinjam(int totalTrxPinjam) {
        this.totalTrxPinjam = totalTrxPinjam;
    }

    public int getTotalTrxPinjam() {
        return totalTrxPinjam;
    }

    public void setTotalTrxPinjam(int totalTrxPinjam) {
        this.totalTrxPinjam = totalTrxPinjam;
    }
}
