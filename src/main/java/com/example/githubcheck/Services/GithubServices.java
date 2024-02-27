package com.example.githubcheck.Services;


import com.example.githubcheck.Dto.BranchDto;
import com.example.githubcheck.Dto.Mapper;
import com.example.githubcheck.Dto.RepositoryDto;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;


@Service
public class GithubServices {
    private final WebClient webClient;
    private final Mapper mapper;


    public GithubServices(WebClient.Builder webBuilder, Mapper mapper) {
        this.webClient = webBuilder.build();

        this.mapper = mapper;
    }


    public Mono<List<RepositoryDto>> getUserRepositories(String username) {
        return webClient.get().uri("/users/{username}/repos", username).header("Accept", "application/json").retrieve().onStatus(HttpStatusCode::is4xxClientError, response -> {
            if (response.statusCode() == HttpStatus.NOT_FOUND) {
                return Mono.error(new UserNotFoundException("User " + username + " not found"));
            }
            return Mono.error(new ResponseStatusException(response.statusCode()));
        }) .bodyToFlux(Repository.class)
                .filter(repository -> !repository.fork())
                .flatMap(repository -> this.getBranchesForRepository(username, repository.name())

                        .map(branches -> mapper.fromRepositoryDto(new Repository(
                                repository.name(),
                                repository.owner(),
                                repository.fork(),
                                branches

                        )))
                )
                .collectList();

    }


    private Mono<List<Branch>> getBranchesForRepository(String username, String repositoryName) {
        return webClient.get().uri("/repos/{username}/{repositoryName}/branches", username, repositoryName).retrieve().bodyToFlux(Branch.class).collectList();

    }
}






