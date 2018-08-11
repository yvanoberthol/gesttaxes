package org.yvanscoop.gesttaxes.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
@Entity
@DiscriminatorValue("TVA")
public class TVA extends Taxe {
    public TVA() {
    }

    public TVA(String titre, Date dateTaxe, double montant, Entreprise entreprise) {
        super(titre, dateTaxe, montant, entreprise);
    }
}
