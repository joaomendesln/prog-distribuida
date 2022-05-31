package com.authentication.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.authentication.controller.GoodController;
import com.authentication.model.Good;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class GoodModelAssembler implements RepresentationModelAssembler<Good, EntityModel<Good>> {

    @Override
    public EntityModel<Good> toModel(Good good) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Good> goodModel = EntityModel.of(good,
                linkTo(methodOn(GoodController.class).one(good.getId())).withSelfRel(),
                linkTo(methodOn(GoodController.class).all()).withRel("goods"));


        return goodModel;
    }
}