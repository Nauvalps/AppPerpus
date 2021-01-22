package com.stmikbanisaleh.appperpus.remote;

public class TransaksiUserRequest {
    private String status;



    public TransaksiUserRequest() {
    }

    public TransaksiUserRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
