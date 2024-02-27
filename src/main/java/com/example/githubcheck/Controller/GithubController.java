package com.example.githubcheck.Controller;


import com.example.githubcheck.Dto.RepositoryDto;
import com.example.githubcheck.Services.GithubServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/repositories")
public class GithubController {
    private final GithubServices services;

    public GithubController(GithubServices services) {
        this.services = services;
    }

    @GetMapping("/{username}/fork=false")
    public ResponseEntity<Mono<List<RepositoryDto>>> getGithubRepositories(@PathVariable String username) {
        return ResponseEntity.ok(services.getUserRepositories(username));


    }
}







