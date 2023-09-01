package com.example.githubcheck.Controller;

import com.example.githubcheck.Exceptions.NotAcceptableException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Services.GithubServices;
import com.example.githubcheck.Model.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Flux<Repository>> getGithubRepositories(@PathVariable String username) {
        try {
            Flux<Repository> userRepositories = services.getUserRepositories(username);
            return ResponseEntity.ok(userRepositories);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotAcceptableException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        }
    }
}







