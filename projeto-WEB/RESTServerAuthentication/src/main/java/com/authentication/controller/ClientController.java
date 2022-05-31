package com.authentication.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.authentication.assembler.ClientModelAssembler;
import com.authentication.exception.ClientChangingPermissionNotAllowedException;
import com.authentication.exception.ClientLoginAlreadyExistsException;
import com.authentication.exception.ClientNotFoundException;
import com.authentication.model.Client;
import com.authentication.repository.ClientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public
class ClientController {
    private final ClientRepository repository;

    private final ClientModelAssembler assembler;

    ClientController(ClientRepository repository, ClientModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/clients")
    public CollectionModel<EntityModel<Client>> all() {

        List<EntityModel<Client>> employees = repository.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(ClientController.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(ClientController.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/clients")
    ResponseEntity<?> newEmployee(@RequestBody Client newClient) {
        List<Client> clients = repository.findAll();

        for (Client c : clients) {
            if (newClient.getLogin().equals(c.getLogin())) throw new ClientLoginAlreadyExistsException(newClient.getLogin());
        }

        newClient.setPermission(0);

        EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item

    @GetMapping("/clients/{id}")
    public EntityModel<Client> one(@PathVariable Long id) {

        Client client = repository.findById(id) //
                .orElseThrow(() -> new ClientNotFoundException(id));

        return EntityModel.of(client, //
                linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("employees"));
    }


    @PutMapping("/clients/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Client newClient, @PathVariable Long id) throws IOException {

        List<Client> clients = repository.findAll();

        for (Client c : clients) {
            if (newClient.getLogin().equals(c.getLogin())) throw new ClientLoginAlreadyExistsException(newClient.getLogin());
        }

        if (newClient.getPermission() == 2){
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/authentication/util/clientWithSavingPermission.txt"));

            List<String> clientsWithSavingPermission = new ArrayList<>();
            String st;
            while ((st = br.readLine()) != null){
                clientsWithSavingPermission = List.of(st.split(","));
            }
            if(!clientsWithSavingPermission.contains(newClient.getLogin())) {
                throw new ClientChangingPermissionNotAllowedException((long) newClient.getPermission());
            }
        }

        if (newClient.getPermission() == 1) {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/authentication/util/clientWithListingPermission.txt"));

            List<String> clientsWithListingPermission = new ArrayList<>();
            String st;
            while ((st = br.readLine()) != null){
                clientsWithListingPermission = List.of(st.split(","));
            }
            if(!clientsWithListingPermission.contains(newClient.getLogin())) {
                throw new ClientChangingPermissionNotAllowedException((long) newClient.getPermission());
            }

        }

        // check if it can be changed
        Client updatedClient = repository.findById(id) //
                .map(employee -> {
                    employee.setLogin(newClient.getLogin());
                    employee.setPersonalName(newClient.getPersonalName());
                    employee.setPassword(newClient.getPassword());
                    employee.setPermission(newClient.getPermission());
                    return repository.save(employee);
                }) //
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });

        EntityModel<Client> entityModel = assembler.toModel(updatedClient);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

//    @DeleteMapping("/clients/{id}")
//    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
//
//        repository.deleteById(id);
//
//        return ResponseEntity.noContent().build();
//    }
}