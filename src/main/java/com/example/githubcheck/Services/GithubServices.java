package com.example.githubcheck.Services;


import com.example.githubcheck.Exceptions.NotAcceptableException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Branch;

import com.example.githubcheck.Model.Repository;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;


@Service
public class GithubServices {
    private final WebClient webClient;


    public GithubServices(WebClient.Builder webBuilder) {
        this.webClient = webBuilder.build();

    }
//

    public Mono<List<Repository>> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToFlux(Repository.class)
                .filter(repository -> !repository.fork())
                .flatMap(repository -> getBranchesForRepository(username,repository.name())
                        .collectList()
                        .map(branches -> repository))
                .collectList();



    }


    private Flux<Branch> getBranchesForRepository(String username, String repositoryName) {
        return webClient
                .get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .retrieve()
                .bodyToFlux(Branch.class);
    }
}






