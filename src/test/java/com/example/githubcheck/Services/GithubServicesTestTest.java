package com.example.githubcheck.Services;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WireMockTest(httpPort = 8080)

class GithubServicesTestTest {
    private final String username = "someUsername";
    private WebTestClient webTestClient;
    private  final String BASE_URL = "http://localhost:8080";


    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl(BASE_URL).build();
    }

    @AfterEach
    void tearDown() {
        WireMock.reset();
    }

    @Test
    void testGetUserRepositoriesSuccess() {

        stubFor(get(urlEqualTo("/repositories/" + username + "/fork=false"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.sampleGithubRepositoryJson())
                        .withStatus(200)));

        webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(TestHelper.sampleGithubRepositoryJson());

    }

    @Test
    void testGetUserRepositoriesNotFound() {


        stubFor(get(urlEqualTo("/repositories/" + username + "/fork=false"))
                .willReturn(aResponse()
                        .withStatus(404).withBody("User " + username + " not found")));

         webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(APPLICATION_JSON)
                .exchange()
                 .expectStatus().isNotFound()
                 .expectBody(String.class)
                 .isEqualTo("User " + username + " not found");


    }
    @Test
    void testGetUserRepositoriesBadRequest() {
        stubFor(get(urlEqualTo("/repositories/" + username + "/fork=false"))
                .willReturn(aResponse()
                        .withStatus(405)
                        .withBody("Method Not Allowed")
                        .withHeader("Content-Type", "application/json")));

        webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .consumeWith(response -> assertNotEquals("User " + username + " not found", response.getResponseBody()));
    }
}





