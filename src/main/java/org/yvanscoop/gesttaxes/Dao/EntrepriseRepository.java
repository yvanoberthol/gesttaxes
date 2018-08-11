package org.yvanscoop.gesttaxes.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yvanscoop.gesttaxes.entities.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise,Long> {
    @Query("select e from Entreprise e where e.nom like :nom order by e.code")
    public Page<Entreprise> chercher(@Param("nom") String nom, Pageable pageable);

    public Entreprise findByNom(String nom);
}
