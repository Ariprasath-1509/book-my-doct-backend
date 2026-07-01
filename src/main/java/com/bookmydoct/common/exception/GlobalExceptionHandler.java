package com.bookmydoct.common.exception;

import com.bookmydoct.common.exception.customException.*;
import com.bookmydoct.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class    GlobalExceptionHandler {

    // ── 404 — User not found ────────────────────────────────────
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── 404 — Role not found ────────────────────────────────────
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFound(RoleNotFoundException ex, HttpServletRequest request) {

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── 409 — User already exists (duplicate email/phone) ───────
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {

        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── 401 — Invalid login credentials ─────────────────────────
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {

        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    //-404->Specialization Not Found----------------------------------
    @ExceptionHandler(SpecializationNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSpecializationNotFound(SpecializationNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //-409->SpecializationAlreadyExists-------------------------------
    @ExceptionHandler(SpecializationAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleSpecializationAlreadyExists(SpecializationAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //=404==>Doctor Not Found-----------------------------------------
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDoctorNotFound(DoctorNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //-409-->DoctorAlreadyExists-------------------------------
    @ExceptionHandler(DoctorAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleDoctorAlreadyExists(DoctorAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //-409-->LicenseAlreadyExists-------------------------------
    @ExceptionHandler(LicenseAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleLicenseAlreadyExists(LicenseAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //=404==>Patient Not Found-----------------------------------------
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePatientNotFound(PatientNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //-409-->PatientAlreadyExists-------------------------------
    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handlePatientAlreadyExists(PatientAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidScheduleException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidSchedule(
            InvalidScheduleException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ScheduleConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleScheduleConflict(
            ScheduleConflictException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //=404==>Patient Not Found-----------------------------------------
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleScheduleNotFound(ScheduleNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SlotGenerationConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleSlotGenerationConflict(
            SlotGenerationConflictException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //-409-->CONFLICT--------------------------------------
    @ExceptionHandler(ScheduleDeletionNotAllowedException.class)
    public ResponseEntity<ApiResponse<Void>> handleScheduleDeletionNotAllowed(ScheduleDeletionNotAllowedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppointmentNotFoundException(AppointmentNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SlotNotAvailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleSlotNotAvailableException(SlotNotAvailableException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaymentAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handlePaymentAlreadyExists(
            PaymentAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePaymentNotFound(
            PaymentNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidPaymentStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPaymentState(
            InvalidPaymentStateException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidAppointmentStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAppointmentStateException(InvalidAppointmentStateException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //=404==>Review Not Found-----------------------------------------
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewNotFound(ReviewNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //-409-->ReviewAlreadyExists-------------------------------
    @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewAlreadyExists(ReviewAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //=404==>Patient Not Found-----------------------------------------
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotificationNotFound(NotificationNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }




    // ── 400 — @Valid validation failures (field-level errors) ───
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = ApiResponse
                .<Map<String, String>>builder()
                .success(false)
                .message("Validation failed")
                .data(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ── 400 — bad request data (e.g. invalid image type/size) ───
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {

        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ── 413 — uploaded file too large ───────────────────────────
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUploadSize(MaxUploadSizeExceededException ex) {

        return buildResponse(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "Uploaded file exceeds the maximum allowed size");
    }

    // ── 500 — fallback for anything unhandled ───────────────────
//    @ExceptionHandler(Exception.class)
    // ── Helper ───────────────────────────────────────────────────
    private ResponseEntity<ApiResponse<Void>> buildResponse(HttpStatus status, String message) {

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .data(null)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}