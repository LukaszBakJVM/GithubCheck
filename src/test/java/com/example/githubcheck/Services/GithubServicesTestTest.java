package com.example.githubcheck.Services;





import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubServicesTestTest {
    @LocalServerPort
    private static int dynamicPort;


    @Autowired
    WebTestClient webTestClient;


    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(dynamicPort))
            .build();
    @BeforeAll
    static void setUp() {
        wireMockServer.getPort();
         WebTestClient.bindToServer().baseUrl(wireMockServer.baseUrl());
    }



    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("baseUrl",wireMockServer::baseUrl);

    }






    @Test
    public void testGetUserRepositoriesSuccess()  {

        WebTestClient.ResponseSpec ok = webTestClient.get()
                .uri("/repositories/octocat/fork=false")
                .exchange()
                .expectStatus().isOk();




    }


    @Test
    public void testGetUserRepositoriesNotFound()  {
        String username = "whenUserNotFoun";

        wireMockServer.stubFor(get(urlPathEqualTo("/users/"+username+"/repos"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));


        webTestClient.get()
                .uri("/repositories/"+username+"/fork=false")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("User "+username+" not found");

    }
 /*  @Test
    public void recordWiremock() throws InterruptedException {
       System.out.println(wireMockServer.getPort());
       while (true) {
           Thread.sleep(4000);
       }*/
   }















