package org.yvanscoop.gesttaxes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Entreprise implements Serializable {

    @Id @GeneratedValue
    private long code;
    @NotNull
    @Size(min = 3, max = 15)
    private String nom;

    @NotNull
    @Size(min = 7, max = 50)
    private String email;

    @NotNull
    @Size(min = 2, max = 10)
    private String raisonSociale;

    @OneToMany(mappedBy = "entreprise",fetch = FetchType.LAZY)
    private Collection<Taxe> taxes;

    public Entreprise() {
    }

    public Entreprise(String nom, String raisonSociale, String email) {
        this.setNom(nom);
        this.setEmail(email);
        this.setRaisonSociale(raisonSociale);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @JsonIgnore
    public Collection<Taxe> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<Taxe> taxes) {
        this.taxes = taxes;
    }
}
