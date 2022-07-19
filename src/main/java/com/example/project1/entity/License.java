package com.example.project1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "licenses")
    private Set<Content> contents;


    public License() {
    }

    public License(Long id, String name, Date startTime, Date endTime, Set<Content> contents) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contents = contents;
    }

    public License(String name, Date startTime, Date endTime, Set<Content> contents) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date setStartTime(Date startTime) {
        return this.startTime = startTime;

    }

    public Date getEndTime() {
        return endTime;
    }

    public Date setEndTime(Date endTime) {
        return this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';


    }


    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }
}




