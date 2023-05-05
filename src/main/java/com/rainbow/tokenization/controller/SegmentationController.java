package com.rainbow.tokenization.controller;

import com.rainbow.tokenization.service.SegmentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SegmentationController {

    @Autowired
    private SegmentationService segmentationService;

    @PostMapping("/segment")
    public ResponseEntity<Map<String, List<String>>> segment(@RequestBody Map<String, String> requestBody) {
        if (requestBody == null || !requestBody.containsKey("text")) {
            return ResponseEntity.badRequest().body(null);
        }
        String text = requestBody.get("text");
        List<String> words = segmentationService.segment(text);
        Map<String, List<String>> response = new HashMap<>();
        response.put("words", words);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> responseBody = Collections.singletonMap("error", ex.getMessage());
        return ResponseEntity.badRequest().body(responseBody);
    }

}
