
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
[
{
"name": "DailyCodingProblem-Problem-46-Hard",
"owner": {
"login": "LukaszBakJVM"
},
"fork": false,
"branches": [
{
"name": "master",
"commit": {
"sha": "8d9ed9b06f7de6a889469d5a0c3da338e63db694"
}
}
]
}

]
Errors
1.Not found GitHub user you will get:
{
"status" : "NOT_FOUND",
"message" : ""User {username} not found"
}
2.Given application/xml Accept header you will get:
{
"status" : "NOT_ACCEPTABLE",
"message" : "No acceptable format"
}