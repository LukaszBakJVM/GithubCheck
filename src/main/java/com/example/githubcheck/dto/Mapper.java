package com.example.githubcheck.dto;

import com.example.githubcheck.model.Branch;
import com.example.githubcheck.model.Owner;
import com.example.githubcheck.model.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {
    public RepositoryDto fromRepositoryToDto(Repository repository) {
        List<BranchDto> list = repository.branches().stream().map(this::fromBranchToDto).findFirst().stream().toList();
        OwnerDto ownerDto = fromOwnerToDto(repository.owner());
        return new RepositoryDto(repository.name(), ownerDto, repository.fork(), list);
    }

    private BranchDto fromBranchToDto(Branch branch) {
        return new BranchDto(branch.name(), branch.commit());
    }

    private OwnerDto fromOwnerToDto(Owner owner) {
        return new OwnerDto(owner.login());
    }


}