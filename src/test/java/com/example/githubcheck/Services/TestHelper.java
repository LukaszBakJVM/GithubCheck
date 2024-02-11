package com.example.githubcheck.Services;

import com.example.githubcheck.Model.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TestHelper {

    static List<Repository>sampleGithubRepository(String json) {



        ObjectMapper objectMapper = new ObjectMapper();

        List<Repository> repositories;

        {
            try {
                repositories = objectMapper.readValue(json, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }


     return   repositories;


    }
   static String json = """
                [
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "repo2020",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "8ee2cf536b0d2a5c09e8f04e66827f1ff4ab2f67"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "Asteroids",
                        "fork": false,
                        "branches": [
                            {
                                "name": "main",
                                "commit": {
                                    "sha": "7ca679fa6ed141691ca45c16987b5fac1b40be9e"
                                }
                            },
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "cbf22dd3509fbcce4f0ef4ab40db4ff314705974"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "itp-w4-car-dealership",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "0ed146212a0d29f84dadf2cffd2b1309f072935a"
                                }
                            },
                            {
                                "name": "solution",
                                "commit": {
                                    "sha": "6d96c47a8c9e15273ff40d1d7fb51896408fda85"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "BGame",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "f838f770c75acba23ec756df7522db5e0cca30fa"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "Cinema_Room_Manager",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "aeba6f2a2c7b8caa63b232034c14b38cf8703e76"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "SimpleAlgorithms",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "99aec1454b9236d10d39c71db228b4b924edf299"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "wordCounterInPDF",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "d32a79fcd20162028c6c86418030eed617b0d0a0"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "search_tree",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "85811e553905982d27b8efca2c6fd2163be88ae2"
                                }
                            }
                        ]
                    },
                    {
                        "owner": {
                            "login": "baranc"
                        },
                        "name": "LambdaFun",
                        "fork": false,
                        "branches": [
                            {
                                "name": "master",
                                "commit": {
                                    "sha": "384722bfcd2a1135b7c0c096b148f532335da3a6"
                                }
                            }
                        ]
                    }
                ]""";


    }




