package com.reactor.ms_customers.domain.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseInfo {

    private boolean isSuccessful;
    private int idResult;
    private String messageResult;

    public static void fillResponse(ResponseInfo instance, boolean isSuccessful, int idResult, String messageResult) throws NumberFormatException {
        if (Objects.nonNull(instance)) {
            if (StringUtils.isEmpty(messageResult) && messageResult.contains("|")) {
                String[] stringMessage = messageResult.split("\\|");
                int parseIdResult = 0;
                Integer.parseInt(stringMessage[0]);
                idResult = parseIdResult;

            }
            instance.setSuccessful(isSuccessful);
            instance.setIdResult(idResult);
            instance.setMessageResult(messageResult);
        }
    }
}
