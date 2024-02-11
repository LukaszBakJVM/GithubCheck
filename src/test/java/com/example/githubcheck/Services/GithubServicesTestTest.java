package com.example.githubcheck.Services;



import com.example.githubcheck.Controller.GithubController;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Repository;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WireMockTest(httpPort = 8080)

@SpringBootTest
class GithubServicesTestTest {


    @Autowired
    GithubController githubController;
    GithubServices githubServices = mock(GithubServices.class);



    @BeforeEach
    void setUp() {
        String BASE_URL = "http://localhost:8080";

    }

    @AfterEach
    void tearDown() {
        WireMock.reset();
    }
    @Test
    public void testGetUserRepositoriesSuccess() {
         String username = "baranc";
        List<Repository> repositories = TestHelper.sampleGithubRepository(TestHelper.json);
        Mono<List<Repository>> just = Mono.just(repositories);



        when(githubServices.getUserRepositories(username)).thenReturn(just);
        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();
        List<Repository> block = githubRepositories.block();
        System.out.println(block.toString());


        StepVerifier.create(githubRepositories)

                .expectNext(TestHelper.sampleGithubRepository(TestHelper.json))
                .expectComplete()
                .verify();


    }




    @Test
    void testGetUserRepositoriesNotFound() {
       String username = "baranczzzz";
        when(githubServices.getUserRepositories(username))
                .thenThrow(WebClientResponseException.NotFound.class);

        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();

        StepVerifier.create(githubRepositories)
                .expectError(UserNotFoundException.class)
                .verify();

    }



    }













