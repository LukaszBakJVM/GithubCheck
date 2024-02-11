package com.example.githubcheck.Model;


import java.util.List;
import java.util.Objects;

public class Repository {
    private Owner owner;
    private String name;

    private boolean fork;
    private List<Branch>branches;

    public String getName() {
        return name;
    }



    public Owner getOwner() {
        return owner;
    }



    public boolean isFork() {
        return fork;
    }



    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return fork == that.fork && Objects.equals(name, that.name) && Objects.equals(owner, that.owner) && Objects.equals(branches, that.branches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, fork, branches);
    }


}
