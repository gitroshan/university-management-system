package com.roshan.university.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.roshan.university.model.Document;
import com.roshan.university.repository.DocumentRepository;
import com.roshan.university.service.FileSystemStorageService;

@Controller
public class DocumentController {

    private Logger log = LoggerFactory.getLogger(DocumentController.class);

    private @Autowired DocumentRepository documentRepository;

    private @Autowired FileSystemStorageService storageService;

    private static String UPLOADED_FOLDER = "/Users/roshanpriyadarshana/Documents/temp/";

    @GetMapping("/documents/result")
    public String result(Model model) {

        List<Document> documents = this.documentRepository.findAll();

        Collections.sort(documents, (document1, document2) -> {
            return document1.getId().intValue() - document2.getId().intValue();
        });

        model.addAttribute("documents", documents);
        return "document/result";
    }

    @GetMapping("/documents")
    public String index(Document document) {
        this.log.info("Loading document form {}.", document);
        return "document/index";
    }

    @GetMapping("/documents/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        System.out.println("--------------------" + filename);

        Resource file = this.storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/documents")
    public String addNewDocument(Document document, BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile file) {

        this.log.info("Submitting document {}", document);
        try {

            if (!StringUtils.hasText(document.getName())) {
                this.log.info("Please provide a document name");
                return "document/index";
            }

            if (file.isEmpty()) {
                this.log.info("No file is uploaded");
                return "document/index";
            }

            document.setFileName(file.getOriginalFilename());
            document.setPath(UPLOADED_FOLDER + "/" + file.getOriginalFilename());

            document.setLink(MvcUriComponentsBuilder
                    .fromMethodName(DocumentController.class, "serveFile", file.getOriginalFilename()).build()
                    .toString());
            this.log.info(document.getPath());

            Optional<Document> documentOptional = this.documentRepository.findByName(document.getName());
            if (documentOptional.isPresent()) {
                this.log.error("Document with name \"{}\" is already exists.", document.getName());
                ObjectError error = new FieldError("document", "name",
                        "Document with name \"" + document.getName() + "\" is already exists.");

                bindingResult.addError(error);

                return "document/index";
            }

            this.storageService.store(file);

            this.documentRepository.save(document);
            model.addAttribute("successMessage", "Document is saved");

            List<Document> documents = this.documentRepository.findAll();

            Collections.sort(documents, (document1, document2) -> {
                return document1.getId().intValue() - document2.getId().intValue();
            });

            model.addAttribute("documents", documents);

            return "redirect:documents/result";
        } catch (Exception e) {

            this.log.error(e.getMessage(), e);
            ObjectError error = new FieldError("document", "name", "Document cannot be saved.");
            bindingResult.addError(error);

            return "document/index";
        }
    }

}
