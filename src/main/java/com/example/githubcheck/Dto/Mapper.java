package com.example.githubcheck.Dto;

import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Owner;
import com.example.githubcheck.Model.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {
    public RepositoryDto fromRepositoryDto(Repository repository) {
        List<BranchDto> list = repository.branches().stream().map(this::fromBranchDto).findFirst().stream().toList();
        OwnerDto ownerDto = fromOwnerDto(repository.owner());
        return new RepositoryDto(repository.name(), ownerDto, repository.fork(), list);
    }

    private BranchDto fromBranchDto(Branch branch) {
        return new BranchDto(branch.name(), branch.commit().sha());
    }

    private OwnerDto fromOwnerDto(Owner owner) {
        return new OwnerDto(owner.login());
    }


}
