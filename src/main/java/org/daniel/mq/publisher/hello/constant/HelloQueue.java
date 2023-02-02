package org.daniel.mq.publisher.hello.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HelloQueue {
    WORLD_EXCHANGE( "world.exchange", "world.key"),
    RABBIT_EXCHANGE( "rabbit.exchange", "rabbit.key");

    private final String queueName = "hello.queue";

    private String name;

    private String routingKey;
}
