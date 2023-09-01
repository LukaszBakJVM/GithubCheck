
GitHub repositories searcher
This is GitHub repositories searcher web app (Atipera task)

Tech Stack
Language: Java 17

Frameworks: Spring Boot 3.12

Build tool: Maven

APIs: GitHub API

UI: REST API

API Reference
Get repos which are not forks, with branches by username
  GET /api/repos/{username}/fork=false?pageSize=&pageNumber=
Parameter	Type	Description
username	string	Required
pageSize	int	Required
pageNumber	int	Required
Example response
[
    {
        "repositoryName":"git-consortium",
        "ownerLogin":"octocat",
        "fork": false,
        "branches":[
            {
                "name":"master",
                "lastCommitSha":"b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
            }
        ]
    },
    {
        "repositoryName":"hello-worId",
        "ownerLogin":"octocat",
        "fork": false,
        "branches":[
            {
                "name":"master",
                "lastCommitSha":"7e068727fdb347b685b658d2981f8c85f7bf0585"
            }
        ]
    },
    {
        "repositoryName":"Hello-World",
        "ownerLogin":"octocat",
        "fork": false,
        "branches":[
            {
                "name":"master",
                "lastCommitSha":"7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
            },
            {
                "name":"octocat-patch-1",
                "lastCommitSha":"b1b3f9723831141a31a1a7252a213e216ea76e56"
            },
            {
                "name":"test",
                "lastCommitSha":"b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf"
            }
        ]
    }
]
Errors
1.Given non existing GitHub user you will get:
{
  "status" : "NOT_FOUND",
  "message" : "User not found"
}
2.Given application/xml Accept header you will get:
{
  "status" : "NOT_ACCEPTABLE",
  "message" : "No acceptable representation"
}
