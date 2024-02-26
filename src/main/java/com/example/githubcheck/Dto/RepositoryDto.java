package com.example.githubcheck.Dto;

import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Owner;

import java.util.List;

public record RepositoryDto(String nameDto, Owner ownerDto, boolean forkDto) {
}
