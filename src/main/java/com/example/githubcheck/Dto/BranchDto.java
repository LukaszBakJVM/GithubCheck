package com.example.githubcheck.Dto;


import com.example.githubcheck.Model.Commit;

public record BranchDto(String name, Commit commit) {
}
