package com.example.githubcheck.Services;


import com.example.githubcheck.Exceptions.NotAcceptableException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Repository;

import reactor.core.publisher.Flux;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;




@Service
public class GithubServices {
    private final WebClient webClient;


    public GithubServices(WebClient.Builder webBuilder) {
        this.webClient = webBuilder.build();

    }


    public Flux<Repository> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Accept", "application/json")
                .retrieve()

                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatusCode.valueOf(404)) {
                        return Mono.error(  new UserNotFoundException("User "+username+" not found") );
                    } else if (response.statusCode()==HttpStatusCode.valueOf(406)) {
                        return Mono.error(new NotAcceptableException("No acceptable format"));

                    }
                    return Mono.error(Throwable::new);

                }).bodyToFlux(Repository.class)
                .filter(repository -> !repository.isFork())
                .flatMap(repository -> this.getBranchesForRepository(username,repository.getName())
                        .collectList()
                        .map(branches -> {repository.setBranches(branches);
                        return repository;}));



    }

    private Flux<Branch> getBranchesForRepository(String username, String repositoryName) {
        return webClient
                .get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .retrieve()
                .bodyToFlux(Branch.class);
    }
}






