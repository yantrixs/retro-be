package com.retro.retrobe.payload;

public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T body;
    private int status;

    public ApiResponse(Boolean success, T body, String message, int status) {
        this.success = success;
        this.message = message;
        this.body = body;
        this.status = status;
    }

    public ApiResponse(Boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
