package org.daniel.mq.publisher.hello.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HelloQueue {
    HELLO( "hello.exchange", "hello.key");

    private final String name = "hello.queue";

    private String exchange;

    private String routingKey;
}
