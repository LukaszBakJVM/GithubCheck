package com.example.githubcheck.Services;


import com.example.githubcheck.Exceptions.NotAcceptableException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;



@Service
public class GithubServices {
    private final WebClient webClient;


    public GithubServices(WebClient.Builder webBuilder) {
        this.webClient = webBuilder.build();

    }


    public Mono<List<Repository>> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new UserNotFoundException("User " + username + " not found"));
                    } else if (response.statusCode() == HttpStatus.NOT_ACCEPTABLE) {
                        return Mono.error(new NotAcceptableException("No acceptable format"));
                    }
                    return response.createException();
                })
                .bodyToMono(new ParameterizedTypeReference<List<Repository>>() {})
                .flatMap(repositories ->
                        Flux.fromIterable(repositories)
                                .filter(repository -> !repository.isFork())
                                .flatMap(repository ->
                                        this.getBranchesForRepository(username, repository.getName())
                                                .collectList()


                                                .map(branches -> {
                                                    repository.setBranches(branches);
                                                    return repository;
                                                })
                                )
                                .collectList()
                );
    }

    private Flux<Branch>getBranchesForRepository(String username, String repositoryName) {
        return webClient
                .get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .retrieve()
                .bodyToFlux(Branch.class);

    }
}






