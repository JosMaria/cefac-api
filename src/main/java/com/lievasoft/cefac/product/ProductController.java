package com.lievasoft.cefac.product;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> getProducts(Authentication authentication, Principal principal) {
        return ResponseEntity.ok("GET all products");
    }

    @GetMapping("/client-info")
    public Map<String, String> getClientInfo(HttpServletRequest request) {
        Map<String, String> clientData = new HashMap<>();

        // IP del cliente (considerando proxies)
        clientData.put("ip", getClientIpAddress(request));

        // User-Agent
        clientData.put("userAgent", request.getHeader("User-Agent"));

        // Método HTTP y URL
        clientData.put("method", request.getMethod());
        clientData.put("url", request.getRequestURL().toString());

        // Headers adicionales
        clientData.put("acceptLanguage", request.getHeader("Accept-Language"));
        clientData.put("referer", request.getHeader("Referer"));

        return clientData;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        // Considera headers comunes de proxies
        String[] headersToCheck = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0]; // Por si hay múltiples IPs
            }
        }

        return request.getRemoteAddr();
    }

    @PostMapping
    public ResponseEntity<String> postProducts() {
        return ResponseEntity.ok("POST product");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProducts() {
        return ResponseEntity.ok("DELETE product");
    }
}
