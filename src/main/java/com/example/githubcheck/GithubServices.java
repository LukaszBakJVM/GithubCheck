package com.example.githubcheck;


import com.example.githubcheck.Exceptions.NotAcceptableException;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class GithubServices {
    private final WebClient webClient;


    public GithubServices(WebClient.Builder webBuilder) {
        this.webClient= webBuilder.build();

    }

    public Flux<Repository> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(status->status.equals(HttpStatus.NOT_FOUND),
                   clientResponse ->      Mono.error(new UserNotFoundException()))
                .onStatus(status->status.equals(HttpStatus.NOT_ACCEPTABLE),
                        clientResponse -> Mono.error(new NotAcceptableException()))
                .bodyToFlux(Repository.class)
                .filter(repository -> !repository.isFork())
                .flatMap(repository -> getBranchesForRepository(username, repository.getName())
                        .collectList()
                        .map(branches -> {
                            repository.setBranches(branches);
                            return repository;
                        }));
    }

    private Flux<Branch> getBranchesForRepository(String username, String repositoryName) {
        return webClient
                .get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .retrieve()
                .bodyToFlux(Branch.class);
    }
}
