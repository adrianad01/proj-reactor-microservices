package com.reactor.ms_customers.domain.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo {

    private boolean successful;  // 🔹 Cambiado de `isSuccessful` a `successful`
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
