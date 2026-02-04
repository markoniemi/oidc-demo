package org.example.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SpaErrorController implements ErrorController {

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        if (status != null && Integer.valueOf(status.toString()) == 404) {
            // If the error happened on an API path, return a JSON error
            if (path != null && path.startsWith("/api")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "API endpoint not found", "path", path));
            }
            // Otherwise, forward to the frontend index.html
            return "forward:/index.html";
        }

        // Handle other errors (500, etc.) as usual
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
