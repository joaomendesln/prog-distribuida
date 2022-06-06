package com.authentication.controller;

import com.authentication.assembler.GoodModelAssembler;
import com.authentication.exception.GoodNotFoundException;
import com.authentication.exception.GoodPermissionNotAllowedException;
import com.authentication.model.Good;
import com.authentication.repository.ClientRepository;
import com.authentication.repository.GoodRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public
class GoodController {

    private final GoodRepository goodRepository;
    private final ClientRepository clientRepository;
    private final GoodModelAssembler assembler;

    GoodController(GoodRepository goodRepository, ClientRepository clientRepository, GoodModelAssembler assembler) {

        this.goodRepository = goodRepository;
        this.clientRepository = clientRepository;
        this.assembler = assembler;
    }

    @GetMapping("/goods")
    public CollectionModel<EntityModel<Good>> all() {

        List<EntityModel<Good>> goods = goodRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(goods, //
                linkTo(methodOn(GoodController.class).all()).withSelfRel());
    }

    @GetMapping("/goods/{id}")
    public EntityModel<Good> one(@PathVariable Long id) {

        Good good = goodRepository.findById(id) //
                .orElseThrow(() -> new GoodNotFoundException(id));

        return assembler.toModel(good);
    }

    @GetMapping("/goods/owner/{id}")
    public CollectionModel<EntityModel<Good>> byOwner(@PathVariable Long id) {

        List<Good> goods = goodRepository.findAll();
        List<Good> goodsByOwnerResult = new ArrayList<>();

        for (Good g : goods) {
            if (g.getClient().getId() == id) goodsByOwnerResult.add(g);
        }

        List<EntityModel<Good>> goodsModel = goodsByOwnerResult.stream()
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(goodsModel, //
                linkTo(methodOn(GoodController.class).all()).withSelfRel());
    }

    @PostMapping("/goods")
    ResponseEntity<EntityModel<Good>> newGood(@RequestBody Good good) {

        if (good.getClient().getPermission() != 2) throw new GoodPermissionNotAllowedException();

        Good newGood = goodRepository.save(good);

        return ResponseEntity //
                .created(linkTo(methodOn(GoodController.class).one(newGood.getId())).toUri()) //
                .body(assembler.toModel(newGood));
    }

}