package com.authentication.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.authentication.controller.ClientController;
import com.authentication.model.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    @Override
    public EntityModel<Client> toModel(Client client) {

        return EntityModel.of(client, //
                linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("employees"));
    }
}