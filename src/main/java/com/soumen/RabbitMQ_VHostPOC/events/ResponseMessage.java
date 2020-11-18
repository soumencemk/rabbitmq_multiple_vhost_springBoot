package com.soumen.RabbitMQ_VHostPOC.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage implements MessageEvent{
    private String clientId;
    private String exchange;
    private String routingKey;
    private String message;

    public static ResponseMessage newDMMResponseMessage(String message){
        return new ResponseMessage("dmm_vhost","amq.direct","sv_response.routing.key",message);

    }

    public static ResponseMessage newPGAResponseMessage(String message){
        return new ResponseMessage("pga_vhost","amq.direct","sv_response.routing.key",message);
    }
}
