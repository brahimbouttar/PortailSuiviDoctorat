package com.gestion.portailsuividoctorat.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "diplomes")
public class Diplomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bac;
    private String licence;
    private String master;

    private String bacFile;
    private String licenceFile;
    private String masterFile;

    public Diplomes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBac() {
        return bac;
    }

    public void setBac(String bac) {
        this.bac = bac;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getBacFile() {
        return bacFile;
    }

    public void setBacFile(String bacFile) {
        this.bacFile = bacFile;
    }

    public String getLicenceFile() {
        return licenceFile;
    }

    public void setLicenceFile(String licenceFile) {
        this.licenceFile = licenceFile;
    }

    public String getMasterFile() {
        return masterFile;
    }

    public void setMasterFile(String masterFile) {
        this.masterFile = masterFile;
    }
}