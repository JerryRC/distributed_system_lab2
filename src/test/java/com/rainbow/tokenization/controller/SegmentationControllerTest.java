package com.rainbow.tokenization.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SegmentationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSegmentation() throws Exception {
        String requestBody = "{\"text\": \"我喜欢自然语言处理\"}";
        MvcResult result = mockMvc.perform(post("/api/segment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> response = mapper.readValue(responseString, new TypeReference<Map<String, List<String>>>() {
        });
        List<String> words = response.get("words");

        assertEquals(Arrays.asList("我", "喜欢", "自然", "语言", "处理"), words);
    }

    @Test
    public void testSegmentationWithEmptyString() throws Exception {
        String requestBody = "{\"text\": \"\"}";
        MvcResult result = mockMvc.perform(post("/api/segment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> response = mapper.readValue(responseString, new TypeReference<>() {
        });
        String words = response.get("error");

        assertEquals("输入不应该为空串哦。", words);
    }

    @Test
    public void testSegmentationWithEmptyBody() throws Exception {
        String requestBody = "";
        MvcResult result = mockMvc.perform(post("/api/segment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();

        assertEquals("", responseString);
    }

    @Test
    public void testSegmentationWithNoText() throws Exception {
        String requestBody = "{\"abc\": \"def\"}";
        MvcResult result = mockMvc.perform(post("/api/segment")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();

        assertEquals("", responseString);
    }

    @Test
    public void testSegmentationWithNoBody() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/segment")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();

        assertEquals("", responseString);
    }

}
