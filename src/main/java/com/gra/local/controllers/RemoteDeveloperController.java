package com.gra.local.controllers;

import com.gra.local.persistence.EntityHelper;
import com.gra.local.persistence.domain.RemoteDeveloper;
import com.gra.local.persistence.services.RemoteDevelopersService;
import com.gra.local.persistence.services.dtos.RemoteDeveloperDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RemoteDeveloperController {

    private RemoteDevelopersService remoteDevelopersService;

    @Autowired
    public RemoteDeveloperController(RemoteDevelopersService remoteDevelopersService) {
        this.remoteDevelopersService = remoteDevelopersService;
    }

    @GetMapping("/developers")
    public ResponseEntity<?> getAllRemoteDevelopers(@RequestParam(name = "firstName") String firstName) {
        return new ResponseEntity<>(EntityHelper.convertToAbstractDto(remoteDevelopersService.getAllRemoteDeveloperWithTheSameLastName(firstName), RemoteDeveloperDto.class), HttpStatus.OK);
    }

    @PostMapping("/developers")
    public ResponseEntity<?> save(@Valid @RequestBody RemoteDeveloperDto remoteDeveloperDto) {
        RemoteDeveloper remoteDeveloper = EntityHelper.convertToAbstractEntity(remoteDeveloperDto, RemoteDeveloper.class);
        RemoteDeveloper savedRemoteDeveloper = remoteDevelopersService.save(remoteDeveloper);
        if (savedRemoteDeveloper == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(remoteDeveloper);
        }
    }
}

