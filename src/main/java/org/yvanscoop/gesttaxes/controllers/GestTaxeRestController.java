package org.yvanscoop.gesttaxes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yvanscoop.gesttaxes.Dao.EntrepriseRepository;
import org.yvanscoop.gesttaxes.Dao.TaxeRepository;
import org.yvanscoop.gesttaxes.entities.Entreprise;
import org.yvanscoop.gesttaxes.entities.Taxe;

import java.util.List;

@RestController
public class GestTaxeRestController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private TaxeRepository taxeRepository;

    @RequestMapping(value = "/pageEntreprises",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Entreprise> listeEntreprises(
            @RequestParam(name = "motcle",defaultValue = "") String motcle,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size) {
        return entrepriseRepository.chercher(motcle+"%",new PageRequest(page,size));
    }

    @RequestMapping(value = "/listeEntreprises",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Entreprise> listeEntreprises() {
        return entrepriseRepository.findAll();
    }

    @RequestMapping(value = "/listeTaxes",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Taxe> listeTaxes(
            @RequestParam(name = "motcle",defaultValue = "")String nom,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "5") int size) {
        return taxeRepository.chercher(nom+"%",new PageRequest(page,size));
    }
}
