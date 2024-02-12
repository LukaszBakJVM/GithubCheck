package com.example.githubcheck.Services;



import com.example.githubcheck.Controller.GithubController;
import com.example.githubcheck.Exceptions.UserNotFoundException;
import com.example.githubcheck.Model.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;





@SpringBootTest
class GithubServicesTestTest {


    @Autowired
    GithubController githubController;





    @Test
    public void testGetUserRepositoriesSuccessNameRepositoryOctocatGithubIo() {
        // given
         String username = "octocat";

        // when
        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();


        // then
        assert githubRepositories != null;
        StepVerifier.create(githubRepositories)
                .expectNextMatches(repositories -> repositories.stream()
                        .anyMatch(repository ->
                                "octocat.github.io".equals(repository.name()) &&
                                "octocat".equals(repository.owner().login()) &&
                                        !repository.fork() &&
                                        repository.branches().stream().anyMatch(branch ->
                                                "gh-pages".equals(branch.name()) &&
                                                        "c0e4a095428f36b81f0bd4239d353f71918cbef3".equals(branch.commit().sha())
                                        )
                        ))
                .expectComplete()
                .verify();
    }
    @Test
    public void testGetUserRepositoriesSuccessNameRepositoryHelloWorld() {
        // given
        String username = "octocat";

        // when
        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();


        // then
        assert githubRepositories != null;
        StepVerifier.create(githubRepositories)
                .expectNextMatches(repositories -> repositories.stream()
                        .anyMatch(repository ->
                                "Hello-World".equals(repository.name()) &&
                                "octocat".equals(repository.owner().login()) &&
                                        !repository.fork() &&
                                        repository.branches().stream().anyMatch(branch ->
                                                "master".equals(branch.name()) &&
                                                        "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d".equals(branch.commit().sha())
                                        )
                        ))
                .expectComplete()
                .verify();
    }
    @Test
    public void testGetUserRepositoriesSuccessNameRepositorySpoonKnife() {
        // given
        String username = "octocat";

        // when
        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();


        // then
        assert githubRepositories != null;
        StepVerifier.create(githubRepositories)
                .expectNextMatches(repositories -> repositories.stream()
                        .anyMatch(repository ->"Spoon-Knife".equals(repository.name()) &&
                                "octocat".equals(repository.owner().login()) &&
                                !repository.fork() &&
                                        repository.branches().stream().anyMatch(branch ->
                                                "change-the-title".equals(branch.name()) &&
                                                        "f439fc5710cd87a4025247e8f75901cdadf5333d".equals(branch.commit().sha())
                                        )
                        ))
                .expectComplete()
                .verify();
    }




    @Test
    void testGetUserRepositoriesNotFound() {
        // given
       String username = "whenUserNotFound";

        // when
        Mono<List<Repository>> githubRepositories = githubController.getGithubRepositories(username).getBody();
        // then
        assert githubRepositories != null;
        StepVerifier.create(githubRepositories)
                .expectError(UserNotFoundException.class)
                .verify();

    }



    }













