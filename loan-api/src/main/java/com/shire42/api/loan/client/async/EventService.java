package com.shire42.api.loan.client.async;

import com.shire42.api.loan.client.async.dto.LoanContractDTO;

public interface EventService {

    void sendEvent(LoanContractDTO dto);

}
