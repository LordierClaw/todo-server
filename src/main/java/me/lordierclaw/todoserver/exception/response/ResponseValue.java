package me.lordierclaw.todoserver.exception.response;

public enum ResponseValue {
    //200x OK
    SUCCESS(200, 2001, "thành công"),
    //400x Bad request
    INVALID_FIELDS(400, 4001, "trường không hợp lệ"),
    INVALID_OR_MISSING_REQUEST_PARAMS(400, 4002, "requests param thiếu hoặc không hợp lệ"),
    INVALID_OR_MISSING_REQUEST_BODY(400, 4003, "request body thiếu hoặc không hợp lệ"),
    FIELD_VALIDATION_ERROR(400, 4004, "lỗi validation trường thông tin"),
    MISSING_CLIENT_ID_OR_SECRET(404, 4005, "thiếu client id hoặc secret"),

    //401x Unauthorized
    AUTHENTICATION_REQUIRED(401, 4011, "truy cập yêu cầu access token để xác thực"),
    WRONG_CLIENT_ID_OR_SECRET(401, 4012, "client id hoặc secret không đúng"),
    WRONG_PASSWORD(401, 4013, "sai mật khẩu"),
    INVALID_TOKEN(401, 4014, "token không hợp lệ"),
    EXPIRED_TOKEN(401, 4015, "token đã hết hạn"),

    //403x Forbidden
    ACCESS_DENIED(403, 4031, "không có quyền truy cập"),

    //404x Not found
    USER_NOT_FOUND(404, 4041, "không tìm thấy người dùng"),
    ITEM_NOT_FOUND(404, 4042, "không tìm thấy nội dung"),

    //409x Conflict
    USERNAME_EXISTS(409, 4091, "tên đăng nhập đã tồn tại"),

    //500x Internal server error
    UNEXPECTED_ERROR_OCCURRED(500, 5000, "lỗi hệ thống");

    private int httpStatus;
    private String message;
    private int specialCode;

    ResponseValue(int httpStatus, int specialCode, String message) {
        this.httpStatus = httpStatus;
        this.specialCode = specialCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public int getSpecialCode() {
        return specialCode;
    }
}
