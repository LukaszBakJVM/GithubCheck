package com.example.githubcheck.Dto;

import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Owner;
import com.example.githubcheck.Model.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Mapper {
    public RepositoryDto fromRepositoryDto(Repository repository) {
        List<BranchDto> branches = repository.branches().stream().map(this::fromBranchDto).collect(Collectors.toList());

        return new RepositoryDto(repository.name(), repository.owner(), repository.fork(), branches);
    }

    public BranchDto fromBranchDto(Branch branch) {
        return new BranchDto(branch.name(), branch.commit());
    }

}
