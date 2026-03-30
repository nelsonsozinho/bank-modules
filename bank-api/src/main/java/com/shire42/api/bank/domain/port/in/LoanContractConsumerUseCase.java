package com.shire42.api.bank.domain.port.in;

import com.shire42.api.bank.domain.model.LoanContractEvent;

public interface LoanContractConsumerUseCase {

    void process(LoanContractEvent event);

}
