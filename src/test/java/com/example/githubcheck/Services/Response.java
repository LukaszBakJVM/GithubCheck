package com.example.githubcheck.Services;


import com.example.githubcheck.Model.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public class Response {

   private static final List<Repository> repositories = loadRepositories();
  static   String jsonData;

    static {
        try {
            jsonData = new ObjectMapper().writeValueAsString(repositories);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private   static List<Repository> loadRepositories()  {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("response.json");
        try {
            return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
