package com.example.githubcheck.Services;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
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
public class GithubServicesTestTest {
    @LocalServerPort
    private int port;

    private WireMockServer wireMockServer;
    @Autowired
    WebTestClient webTestClient;




    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(1111));
        wireMockServer.start();
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + wireMockServer.port())
                .build();


    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testExternalApi() {

        StubMapping stubMapping = wireMockServer.stubFor(get(urlPathEqualTo("/api/endpoint"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"message\": \"Hello from external server!\"}")));
        System.out.println(stubMapping.getRequest());

        webTestClient.get()
                .uri("/api/endpoint")
                .exchange()
                .expectStatus().isOk();


    }








    }













