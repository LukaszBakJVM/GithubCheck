package com.example.githubcheck.Dto;

import com.example.githubcheck.Model.Branch;
import com.example.githubcheck.Model.Owner;
import com.example.githubcheck.Model.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Mapper {
    public RepositoryDto fromRepositoryDto(Repository repository) {

        List<Branch> list = repository.branches().stream().toList();



        return new RepositoryDto(repository.name(), repository.owner(), repository.fork());
    }

    public BranchDto fromBranchDto(Branch branch) {
        return new BranchDto(branch.name(), branch.commit());
    }
  public Record RepositoryDto  (Repository repository){
      List<BranchDto> list = repository.branches().stream().map(this::branchDto).toList();
      return new RepositoryDto(repository.name(),repository.owner(),repository.fork());


    }
    public BranchDto branchDto(Branch branch){
        return new BranchDto(branch.name(), branch.commit());
    }


}
