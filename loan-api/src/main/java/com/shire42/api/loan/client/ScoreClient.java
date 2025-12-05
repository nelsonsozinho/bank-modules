package com.shire42.api.loan.client;

import com.shire42.api.loan.client.configuration.FeignSslConfig;
import com.shire42.api.loan.client.dto.ClientScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "${api.score.name}",
        url = "${api.score.url}",
        configuration = FeignSslConfig.class)
public interface ScoreClient {

    @GetMapping("/client/{cpf}")
    ClientScore getScoreUser(@PathVariable("cpf") String cpf);

}
