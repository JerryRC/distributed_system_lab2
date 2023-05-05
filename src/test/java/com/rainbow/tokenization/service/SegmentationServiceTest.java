package com.rainbow.tokenization.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class SegmentationServiceTest {

    @Autowired
    private SegmentationService segmentationService;

    @Test
    void testSegment() {
        String text = "我爱北京天安门";
        List<String> result = segmentationService.segment(text);
        assertEquals(Arrays.asList("我", "爱", "北京", "天安门"), result);
    }

    @Test
    public void testSegmentationWithEmptyText() {
        String text = "";
        assertThrows(IllegalArgumentException.class, () -> segmentationService.segment(text));
    }

}

