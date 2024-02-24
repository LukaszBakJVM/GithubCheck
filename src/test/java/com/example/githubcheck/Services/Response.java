package com.example.githubcheck.Services;


import com.example.githubcheck.Model.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public class Response {



     static List<Repository> loadRepositories()  {
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
