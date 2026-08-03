package org.wilsonks.slotapigateway.security.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/auth")
    public ResponseEntity<Map<String, Object>> authFallback(HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", 503,
                        "error", "Service Unavailable",
                        "by", "FallbackController",
                        "message", "Authentication service is temporarily unavailable."
                ));
    }

    @RequestMapping("/bank")
    public ResponseEntity<Map<String, Object>> bankFallback(HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", 503,
                        "error", "Service Unavailable",
                        "by", "FallbackController",
                        "message", "Bank service is temporarily unavailable."
                ));
    }

    @RequestMapping("/floor")
    public ResponseEntity<Map<String, Object>> floorFallback(HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", 503,
                        "error", "Service Unavailable",
                        "by", "FallbackController",
                        "message", "Floor management service is temporarily unavailable."
                ));
    }
}
