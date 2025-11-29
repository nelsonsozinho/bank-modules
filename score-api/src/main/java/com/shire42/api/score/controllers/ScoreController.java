package com.shire42.api.score.controllers;

import com.shire42.api.score.controllers.rest.in.ScoreRestInput;
import com.shire42.api.score.controllers.rest.out.ScoreRestOut;
import com.shire42.api.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService accountService;

    @GetMapping("/client/{cpf}")
    public ResponseEntity<ScoreRestOut> getAccountByNumber(@PathVariable("cpf") final String cpf) {
        return ResponseEntity.ok(accountService.findScoreByClientCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<?> createUpdateScore(@RequestBody final ScoreRestInput input) throws URISyntaxException {
        String cpf = accountService.updateScore(input.getCpf(), input.getScore());
        StringBuilder builder = new StringBuilder();
        builder.append("/score/client/").append(cpf);
        return ResponseEntity.created(new URI(builder.toString())).build();
    }


}
