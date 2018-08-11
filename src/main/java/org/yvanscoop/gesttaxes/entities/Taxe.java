package org.yvanscoop.gesttaxes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 3)
public abstract class Taxe implements Serializable {
    @Id @GeneratedValue
    private long id;
    @NotNull
    @Size(min = 4, max = 25)
    private String titre;
    @Temporal(TemporalType.DATE)
    private Date dateTaxe;
    @NotNull
    @Positive
    private double montant;

    @ManyToOne
    @JoinColumn(name = "code_entreprise")
    private Entreprise entreprise;

    public Taxe() {
    }

    public Taxe(String titre, Date dateTaxe, double montant, Entreprise entreprise) {
        this.titre = titre;
        this.dateTaxe = dateTaxe;
        this.montant = montant;
        this.entreprise = entreprise;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateTaxe() {
        return dateTaxe;
    }

    public void setDateTaxe(Date dateTaxe) {
        this.dateTaxe = dateTaxe;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @JsonIgnore
    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}
