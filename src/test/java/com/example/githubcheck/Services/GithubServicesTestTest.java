package com.example.githubcheck.Services;





import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GithubServicesTestTest {
    @LocalServerPort
    private static int dynamicPort;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    WebTestClient webTestClient;


    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(dynamicPort))
            .build();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("baseUrl", wireMockServer::baseUrl);

    }


    @Test
    public void testGetUserRepositoriesSuccess() {
        List<String> expectedRepositories = Arrays.asList(
                "{\"name\":\"octocat.github.io\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"gh-pages\",\"commit\":{\"sha\":\"c0e4a095428f36b81f0bd4239d353f71918cbef3\"}},{\"name\":\"master\",\"commit\":{\"sha\":\"3a9796cf19902af0f7e677391b340f1ae4128433\"}}]}",
                "{\"name\":\"git-consortium\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"master\",\"commit\":{\"sha\":\"b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e\"}}]}",
                "{\"name\":\"hello-worId\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"master\",\"commit\":{\"sha\":\"7e068727fdb347b685b658d2981f8c85f7bf0585\"}}]}",
                "{\"name\":\"Hello-World\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"master\",\"commit\":{\"sha\":\"7fd1a60b01f91b314f59955a4e4d4e80d8edf11d\"}},{\"name\":\"octocat-patch-1\",\"commit\":{\"sha\":\"b1b3f9723831141a31a1a7252a213e216ea76e56\"}},{\"name\":\"test\",\"commit\":{\"sha\":\"b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf\"}}]}",
                "{\"name\":\"test-repo1\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"gh-pages\",\"commit\":{\"sha\":\"57523742631876181d95bc268e09fb3fd1a4d85e\"}}]}",
                "{\"name\":\"Spoon-Knife\",\"owner\":{\"login\":\"octocat\"},\"fork\":false,\"branches\":[{\"name\":\"change-the-title\",\"commit\":{\"sha\":\"f439fc5710cd87a4025247e8f75901cdadf5333d\"}},{\"name\":\"main\",\"commit\":{\"sha\":\"d0dd1f61b33d64e29d8bc1372a94ef6a2fee76a9\"}},{\"name\":\"test-branch\",\"commit\":{\"sha\":\"58060701b538587e8b4ab127253e6ed6fbdc53d1\"}}]}"
        );


    /*   stubFor(get(urlPathEqualTo("/users/octocat/repos"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));




       stubFor(get(urlPathEqualTo("/repos/octocat/*"))
                    .willReturn(aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));*/


        WebTestClient.BodyContentSpec json = webTestClient.get()
                .uri("/repositories/octocat/fork=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(Arrays.toString(expectedRepositories.toArray(new String[0])));


    }


    @Test
    public void testGetUserRepositoriesNotFound() {
        String username = "whenUserNotFoun";

        wireMockServer.stubFor(get(urlPathEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));


        webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("User " + username + " not found");


    }

   /* @Test
  public void recordWiremock() throws InterruptedException {
        System.out.println(wireMockServer.getPort());
        while (true) {
            Thread.sleep(4000);
        }
    }*/
}














