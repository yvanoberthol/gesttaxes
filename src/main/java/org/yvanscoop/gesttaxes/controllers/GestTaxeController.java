package org.yvanscoop.gesttaxes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yvanscoop.gesttaxes.Dao.EntrepriseRepository;
import org.yvanscoop.gesttaxes.Dao.TaxeRepository;
import org.yvanscoop.gesttaxes.entities.Entreprise;
import org.yvanscoop.gesttaxes.entities.IR;
import org.yvanscoop.gesttaxes.entities.Taxe;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@Controller
public class GestTaxeController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private TaxeRepository taxeRepository;

    @RequestMapping(value="/entreprises",method=RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(name = "motcle",defaultValue = "") String motcle,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "3") int size){
        Page<Entreprise> entreprises = entrepriseRepository.chercher("%"+motcle+"%",new PageRequest(page,size) {});
        model.addAttribute("listeEntreprises",entreprises.getContent());

        int[] pages = new int[entreprises.getTotalPages()];
        model.addAttribute("pages",pages);
        model.addAttribute("pageCourante",page);
        //le mot cle
        model.addAttribute("motcle",motcle);
        return "entreprises";
    }

    @RequestMapping(value="/formEntreprise",method=RequestMethod.GET)
    public String ajouterEntreprise(Model model){
        model.addAttribute("entreprise",new Entreprise());
        return "ajoutEntreprise";
    }

    @RequestMapping(value="/ajoutEntreprise",method=RequestMethod.POST)
    public String saveEntreprise(Model model, @Valid Entreprise entreprise, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            entrepriseRepository.save(entreprise);
            return "redirect:/entreprises";
        }else{
            return "ajoutEntreprise";
        }
    }

    @RequestMapping(value="/formTaxe",method=RequestMethod.GET)
    public String ajouterTaxe(Model model){
        model.addAttribute("taxe",new IR());
        List<Entreprise> entreprises= entrepriseRepository.findAll();
        model.addAttribute("entreprises",entreprises);
        return "ajoutTaxe";
    }

    @RequestMapping(value="/ajoutTaxe",method=RequestMethod.POST)
    public String saveTaxe(Model model, @Valid IR taxe, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            taxeRepository.save(taxe);
            return "redirect:/alltaxes";
        }else{
            return "ajoutTaxe";
        }
    }

    @RequestMapping(value="/taxes",method=RequestMethod.GET)
    public String taxes(Model model,
                        @RequestParam(name = "code",defaultValue = "1")String code,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "3") int size){
        Page<Taxe> taxes = taxeRepository.chercher(code,new PageRequest(page,size));
        model.addAttribute("listeTaxes",taxes.getContent());

        int[] pages = new int[taxes.getTotalPages()];
        model.addAttribute("pages",pages);
        model.addAttribute("pageCourante",page);
        List<Entreprise> entreprises= entrepriseRepository.findAll();
        model.addAttribute("entreprises",entreprises);
        model.addAttribute("code",code);
        return "taxes";
    }

   @RequestMapping(value = "/alltaxes", method = RequestMethod.GET)
    public String alltaxes(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "3") int size) {
        Page<Taxe> taxes = taxeRepository.chercher(new PageRequest(page, size));
        model.addAttribute("listeTaxes", taxes.getContent());

        int[] pages = new int[taxes.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("pageCourante", page);
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        model.addAttribute("entreprises", entreprises);
        return "alltaxes";
    }

}
