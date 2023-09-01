package com.example.githubcheck.Controller;

import com.example.githubcheck.Services.GithubServices;
import com.example.githubcheck.Model.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;



@RestController
@RequestMapping("/repositories")
public class GithubController {
    private final GithubServices services;

    public GithubController(GithubServices services) {
        this.services = services;
    }
    @GetMapping("/{username}/fork=false")
    public Flux<Repository> getGithubRepositories(@PathVariable String username) {

        return services.getUserRepositories(username);
    }


    }






