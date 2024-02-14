package com.example.githubcheck.Services;





import com.example.githubcheck.Controller.GithubController;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.github.tomakehurst.wiremock.WireMockServer;


import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
//import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.reactive.server.WebTestClient;





import static com.github.tomakehurst.wiremock.client.WireMock.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient

class GithubServicesTestTest {
    @LocalServerPort
    private static int randomPort;
    @Autowired
    GithubServices githubServices;
    @Autowired
    GithubController githubController;





@Autowired
    private  WebTestClient webTestClient;

    private  WireMockServer wireMockServer;
    @BeforeEach
    public  void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(randomPort));
        wireMockServer.start();
    }
    @AfterEach
    public  void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testWebClientWithWireMock() throws JsonProcessingException {



        StubMapping stubMapping = wireMockServer.stubFor(
                get(urlPathEqualTo("/users/{username}/repos"))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));






         webTestClient.get()
                .uri("/repositories/octocat/fork=false")
                .exchange()
                .expectStatus().isOk();




    }



}









