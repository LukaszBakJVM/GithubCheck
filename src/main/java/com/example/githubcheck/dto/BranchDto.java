package com.example.githubcheck.dto;


import com.example.githubcheck.model.Commit;

public record BranchDto(String name, Commit commit) {
}
