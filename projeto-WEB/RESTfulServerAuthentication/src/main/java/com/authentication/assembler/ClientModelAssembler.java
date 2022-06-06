package com.authentication.assembler;

import com.authentication.controller.ClientController;
import com.authentication.model.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public
class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    @Override
    public EntityModel<Client> toModel(Client client) {

        return EntityModel.of(client, //
                WebMvcLinkBuilder.linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("employees"));
    }
}