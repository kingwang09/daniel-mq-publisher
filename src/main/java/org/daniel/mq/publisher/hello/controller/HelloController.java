package org.daniel.mq.publisher.hello.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.daniel.mq.publisher.hello.constant.HelloQueue;
import org.daniel.mq.publisher.hello.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hello")
public class HelloController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping
    public String hello(@RequestParam(required = false, defaultValue = "daniel") String name, @RequestParam(required = false, defaultValue = "hello world!") String message){
        try {
            rabbitTemplate.convertAndSend(HelloQueue.HELLO.getExchange(), HelloQueue.HELLO.getRoutingKey(), Message.builder().name(name).message(message).build());
        }catch (Exception ex){
            log.warn("mq send fail: {}", ex.getMessage(), ex);
        }
        return String.format("Hi, %s sir, send Queue: %s", name, message);
    }
}
