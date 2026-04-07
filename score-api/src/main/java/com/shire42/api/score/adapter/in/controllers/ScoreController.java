package com.shire42.api.score.adapter.in.controllers;

import com.shire42.api.score.adapter.in.controllers.rest.in.ScoreRestInput;
import com.shire42.api.score.adapter.in.controllers.rest.out.ScoreRestOut;
import com.shire42.api.score.domain.port.in.FindScoreUseCase;
import com.shire42.api.score.domain.port.in.NewScoreUseCase;
import com.shire42.api.score.domain.port.in.UpdateScoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ScoreController {

    private final FindScoreUseCase findScoreUseCase;

    private final UpdateScoreUseCase updateScoreUseCase;

    private final NewScoreUseCase newScoreUseCase;


    @GetMapping("/client/{cpf}")
    public ResponseEntity<ScoreRestOut> getAccountByNumber(@PathVariable final String cpf) {
        var score = findScoreUseCase.findScoreByClientCpf(cpf);
        return ResponseEntity.ok(
                ScoreRestOut.builder()
                        .cpf(score.getClient().getCpf())
                        .lastUpdate(score.getLastUpdate())
                        .score(new BigDecimal(score.getScore()))
                .build());
    }

    @PutMapping
    public ResponseEntity<?> updateScore(@RequestBody final ScoreRestInput input) throws URISyntaxException {
        return ResponseEntity
                .created(new URI("/score/client/" + updateScoreUseCase.updateScore(input.getCpf(), input.getScore())))
                .build();
    }



}
