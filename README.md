# GithubCheck
# GithubCheck
GitHub repositories searcher

Tech Stack
Language: Java 17

Frameworks: Spring Boot 3.1.3

Build tool: Maven

APIs: GitHub API

UI: REST API

API Reference
Get repos which are not forks, with branches by username
  GET /repositories/{username}/fork=false
Parameter	Type	Description
username	string	Required

Example response
{
        "name": "DailyCodingProblemProblem-88-Medium",
        "owner": {
            "login": "LukaszBakJVM"
        },
        "fork": false,
        "branches": [
            {
                "name": "master",
                "commit": {
                    "sha": "5ffc46d75247051049389f0e33135f91e6758b3a"
                }
            }
        ]
    },


Errors

2.Given application/xml Accept header you will get:
{
  "status" : "NOT_ACCEPTABLE",
  "message" : "No acceptable formatg"
}
