package com.soumen.RabbitMQ_VHostPOC.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignMessage implements MessageEvent{
    private String clientId;
    private String exchange;
    private String routingKey;
    private String message;

    public static SignMessage newDMMSignMessage(String message){
        return new SignMessage("dmm_vhost","amq.direct","sv_sign.routing.key",message);

    }

    public static SignMessage newPGASignMessage(String message){
        return new SignMessage("pga_vhost","amq.direct","sv_sign.routing.key",message);
    }
}
