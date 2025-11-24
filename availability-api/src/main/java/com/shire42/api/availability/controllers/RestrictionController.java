package com.shire42.api.availability.controllers;

import com.shire42.api.availability.controllers.rest.in.RestrictionJsonInput;
import com.shire42.api.availability.controllers.rest.out.RestrictionJsonOutput;
import com.shire42.api.availability.service.RestrictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/restriction", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestrictionController {

    private final RestrictionService sercice;

    @GetMapping("/{id}")
    public ResponseEntity<RestrictionJsonOutput> getRestrictionById(@PathVariable("id") final Long id) {
        var restriction = sercice.getRestrictionById(id);
        return ResponseEntity.ok(RestrictionJsonOutput.builder()
                .id(restriction.getId())
                .lastUpdate(restriction.getLastUpdate())
                .source(restriction.getSource())
                .value(new BigDecimal(restriction.getValue()))
                .build());
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<List<RestrictionJsonOutput>> listAllRestrictionsByCPF(@PathVariable("cpf") final String cpf) {
        return ResponseEntity.ok(sercice.listCreditRestrictions(cpf).stream().map(r -> RestrictionJsonOutput.builder()
                .lastUpdate(r.getLastUpdate())
                .source(r.getSource())
                .value(new BigDecimal(r.getValue()))
                .id(r.getId())
                .build()).toList()
        );
    }

    @PostMapping("/client/{cpf}")
    public ResponseEntity<?> createNewRestriction(@RequestBody RestrictionJsonInput restriction, @PathVariable String cpf) {
        var id = sercice.addRestriction(cpf, restriction.getValue(), restriction.getSource());
        return ResponseEntity.created(URI.create(String.format("/restriction/%d", id))).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRestriction(
            @RequestBody final RestrictionJsonInput restriction,
            @PathVariable("id") final Long id) {
        sercice.updateRestriction(id, restriction.getValue(), restriction.getSource(), restriction.getIssActivated());
        return ResponseEntity.ok().build();
    }

}
