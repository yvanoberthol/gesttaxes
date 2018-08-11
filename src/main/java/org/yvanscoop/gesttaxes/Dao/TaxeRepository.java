package org.yvanscoop.gesttaxes.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yvanscoop.gesttaxes.entities.Entreprise;
import org.yvanscoop.gesttaxes.entities.Taxe;

import java.util.List;

public interface TaxeRepository extends JpaRepository<Taxe,Long> {
    public List<Taxe> findByEntreprise(Entreprise e);

    @Query("select t from Taxe t where t.entreprise in (select e from Entreprise e where e.nom like :nom) order by t.id")
    public Page<Taxe> chercher(@Param("nom") String nom, Pageable pageable);

    @Query("select t from Taxe t order by t.id")
    public Page<Taxe> chercher(Pageable pageable);

}
