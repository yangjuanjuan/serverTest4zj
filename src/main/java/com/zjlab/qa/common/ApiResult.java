package com.zjlab.qa.common;

public class ApiResult<T> {

    private boolean isSuccess;

    private int code;

    private String message;

    private String tips;

    private T result;


    private ApiResult(boolean isSuccess, int code, String message, T result, String tips) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
        this.tips = tips;

    }

    public ApiResult() {
        this(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage());
    }

    public ApiResult(int code, String message) {
        this(code == ApiResultCode.SUCCESS.getCode(), code, message, null, null);
    }

    public static <T> ApiResult<T> valueOf(T result) {
        return ApiResult.valueOf(ApiResultCode.SUCCESS, result);
    }

    public static <T> ApiResult<T> valueOf(ApiResultCode apiResultCode) {
        return ApiResult.valueOf(apiResultCode, null);
    }

    public static <T> ApiResult<T> valueOf(ApiResultCode apiResultCode, T result) {
        return ApiResult.valueOf(apiResultCode, result, null);
    }

    public static <T> ApiResult<T> valueOf(ApiResultCode apiResultCode, T result, String tips) {
        if (apiResultCode == ApiResultCode.SUCCESS) {
            return new ApiResult<T>(true, apiResultCode.getCode(), apiResultCode.getMessage(), result, tips);
        } else {
            return new ApiResult<T>(false, apiResultCode.getCode(), apiResultCode.getMessage(), result, tips);
        }
    }

}