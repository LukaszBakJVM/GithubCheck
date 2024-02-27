package com.example.githubcheck.Dto;

import java.util.List;

public record RepositoryDto(String nameDto, OwnerDto ownerDto, boolean forkDto, List<BranchDto> branchesDto) {
}
