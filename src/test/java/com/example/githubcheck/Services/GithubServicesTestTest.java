package com.example.githubcheck.Services;





import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.Arrays;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GithubServicesTestTest {
    @LocalServerPort
    private static int dynamicPort;

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


        String username = "octocat";


        webTestClient.get()
                .uri("/repositories/"+username+"/fork=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(Arrays.toString(Response.testData));

    }

    @Test
    public void testGetUserRepositoriesNotFound() {
        String username = "whenUserNotFound";
        String jsonMessage = "{\"message\": \"User whenUserNotFound not found\"}";


        webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json(jsonMessage);


    }
    @Test
    public void testGetUserRepositoriesForbidden() {
        String username = "LukaszBakJVM";



        webTestClient.get()
                .uri("/repositories/" + username + "/fork=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden();




    }


 /*   @Test
  public void recordWiremock() throws InterruptedException {
        System.out.println(wireMockServer.getPort());
        while (true) {
            Thread.sleep(4000);
        }
    }*/
}














