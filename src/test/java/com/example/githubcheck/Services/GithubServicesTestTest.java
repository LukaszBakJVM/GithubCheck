package com.example.githubcheck.Services;





import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
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
@AutoConfigureWebTestClient
public class GithubServicesTestTest {
    @LocalServerPort
    private static int port;


    @Autowired
    WebTestClient webTestClient;


    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(port))
            .build();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("baseUrl",wireMockServer::baseUrl);

    }






    @Test
    public void testGetUserRepositoriesSuccess()  {


            webTestClient.options().uri(wireMockServer.baseUrl());


        StubMapping stubMapping = wireMockServer.stubFor(get(urlPathEqualTo("/users/octocat/repos"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        System.out.println(stubMapping.getResponse());


        wireMockServer.stubFor(get(urlPathEqualTo("/repos/octocat/git-consortium/branches"))
                    .willReturn(aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)))
                .getMetadata();


         webTestClient.get()
                .uri("/repositories/octocat/fork=false")
                .exchange()
                .expectStatus().isOk()
                .expectBody();





    }


    @Test
    public void testGetUserRepositoriesNotFound()  {
        String username = "whenUserNotFound";
        webTestClient.options().uri(wireMockServer.baseUrl());
        StubMapping stubMapping = wireMockServer.stubFor(get(urlPathEqualTo("/users/"+username+"/repos"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));
        System.out.println(stubMapping.getRequest());

        webTestClient.get()
                .uri("/repositories/whenUserNotFound/fork=false")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("User "+username+" not found");

    }
  /*  @Test
    public void recordWiremock() throws InterruptedException {
        System.out.println(wireMockServer.getPort());
        while (true){
            Thread.sleep(4000);
        }*/












    }













