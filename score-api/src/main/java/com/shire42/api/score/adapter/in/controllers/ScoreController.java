package com.shire42.api.score.adapter.in.controllers;

import com.shire42.api.score.adapter.in.controllers.rest.in.ScoreRestInput;
import com.shire42.api.score.adapter.in.controllers.rest.out.ScoreRestOut;
import com.shire42.api.score.domain.port.in.FindScoreUseCase;
import com.shire42.api.score.domain.port.in.NewScoreUseCase;
import com.shire42.api.score.domain.servuce.UpdateScoreService;
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

    private final UpdateScoreService updateScoreService;

    private final NewScoreUseCase newScoreUseCase;


    @GetMapping("/client/{cpf}")
    public ResponseEntity<ScoreRestOut> getAccountByNumber(@PathVariable("cpf") final String cpf) {
        var score = findScoreUseCase.findScoreByClientCpf(cpf);
        return ResponseEntity.ok(
                ScoreRestOut.builder()
                        .cpf(score.getClient().getCpf())
                        .lastUpdate(score.getLastUpdate())
                        .score(new BigDecimal(score.getScore()))
                .build());
    }

    @PostMapping
    public ResponseEntity<?> createNewScore(@RequestBody final ScoreRestInput input) throws URISyntaxException {
        var score = newScoreUseCase.newScore(input.getCpf(), input.getScore());
        return ResponseEntity.created(new URI("/score/client/" + score.getClient().getCpf())).build();
    }

    @PutMapping
    public ResponseEntity<?> updateScore(@RequestBody final ScoreRestInput input) throws URISyntaxException {
        String cpf = updateScoreService.updateScore(input.getCpf(), input.getScore());
        return ResponseEntity.created(new URI("/score/client/" + cpf)).build();
    }


}
