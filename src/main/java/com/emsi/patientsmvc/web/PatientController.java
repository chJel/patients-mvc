package com.emsi.patientsmvc.web;
import java.util.Collections;

import com.emsi.patientsmvc.PatientsMvcApplication;
import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
@AllArgsConstructor
@Controller
public class PatientController {

    private PatientRepository patientRepository;
@GetMapping(path = "/user/index")
   public String patients(Model model,
       @RequestParam(name = "page",defaultValue ="0" ) int page,
       @RequestParam(name = "size",defaultValue ="5" )int size,
       @RequestParam(name = "keyword",defaultValue ="" )String keyword  ) {
    Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size
    ));
    model.addAttribute("listPatients",pagePatients.getContent());
    model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
    model.addAttribute("currentPage",page);
    model.addAttribute("keyword",keyword);
    return "patients";
   }
   @GetMapping("/admin/delete")
   public String delete(@RequestParam(name="id") long id,String keyword,int page){
    patientRepository.deleteById(id);
    return"redirect:/index?page="+page+"&keyword="+keyword;
   }
   @GetMapping("/")
   public String home(){
       return"redirect:/user/index";
   }
   @GetMapping(path="/patients")
   @ResponseBody
   public List<Patient> listPatients(){
    return patientRepository.findAll();
   }
   @GetMapping("/admin/formPatients")
   public String formPatient(Model model){
    model.addAttribute("patient",new Patient());
    return"formPatients";
   }
   @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
    if(bindingResult.hasErrors()) return "formPatients";
    patientRepository.save(patient);
    return "redirect:/index?page"+page+"&keyword="+keyword;

    }
    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,Long id,String keyword,int page){
    Patient patient=patientRepository.findById(id).orElse(null
    );
    if(patient==null) throw  new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        return"editPatient";
    }


}

