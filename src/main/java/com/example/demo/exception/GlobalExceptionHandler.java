// package com.example.demo.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.context.request.WebRequest;

// @RestControllerAdvice
// public class GlobalExceptionHandler {
    
//     @ExceptionHandler(BadRequestException.class)
//     public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
//         ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//     }
    
//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//         ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//         return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//     }
    
//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
//         ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + ex.getMessage());
//         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//     }
    
//     public static class ErrorResponse {
//         private int status;
//         private String message;
        
//         public ErrorResponse(int status, String message) {
//             this.status = status;
//             this.message = message;
//         }
        
//         public int getStatus() { return status; }
//         public void setStatus(int status) { this.status = status; }
        
//         public String getMessage() { return message; }
//         public void setMessage(String message) { this.message = message; }
//     }
// }