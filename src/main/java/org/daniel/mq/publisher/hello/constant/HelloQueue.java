package org.daniel.mq.publisher.hello.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HelloQueue {
    WORLD_EXCHANGE( "world.key"),
    RABBIT_EXCHANGE( "rabbit.key");


    private String routingKey;
}
