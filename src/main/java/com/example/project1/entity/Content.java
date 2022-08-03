package com.example.project1.entity;

import javax.persistence.*;
import java.util.Set;

@Entity

public class Content{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String status;

    @ManyToMany(targetEntity = License.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "content_licence",
            joinColumns = {@JoinColumn(name = "content_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "license_id", nullable = false, updatable = false)})
    private Set<License> licenses;
    private String posterUrl;
    private String videoUrl;

    public Content() {
    }

    public Content(String name, String status, Set<License> licenses, String posterUrl, String videoUrl) {
        this.name = name;
        this.status = status;
        this.posterUrl = posterUrl;
        this.videoUrl = videoUrl;
        this.licenses = licenses;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getposterUrl() {
        return posterUrl;
    }

    public void setposterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<License> licenses) {
        this.licenses = licenses;
    }

    @Override
    public String toString() {
        return "Content{" + "id=" + id + ", name='" + name + '\'' + ", status=" + status + ", licenses=" + licenses + ", posterUrl=" + posterUrl + ", videoUrl=" + videoUrl + '}';
    }


}