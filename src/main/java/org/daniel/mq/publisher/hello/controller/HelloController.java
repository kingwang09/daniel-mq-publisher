package org.daniel.mq.publisher.hello.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.daniel.mq.publisher.hello.constant.HelloQueue;
import org.daniel.mq.publisher.hello.entity.CustomMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hello")
public class HelloController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/world")
    public CustomMessage world(@RequestParam(required = false, defaultValue = "daniel") String name, @RequestParam(required = false, defaultValue = "hello world!") String message){
        var customMessage = CustomMessage.builder().name(name).message(message).registeredAt(LocalDateTime.now()).build();
        try {
            log.debug("send world exchange: name={}, message={}", name, message);
            rabbitTemplate.convertAndSend(HelloQueue.WORLD_EXCHANGE.name(), HelloQueue.WORLD_EXCHANGE.getRoutingKey(), customMessage);
        }catch (Exception ex){
            log.warn("send world exchange fail: {}", ex.getMessage(), ex);
        }
        return customMessage;
    }

    @GetMapping("/rabbit")
    public CustomMessage rabbit(@RequestParam(required = false, defaultValue = "daniel") String name, @RequestParam(required = false, defaultValue = "hello world!") String message){
        var customMessage = CustomMessage.builder().name(name).message(message).build();
        try {
            log.debug("send rabbit exchange: name={}, message={}", name, message);
            rabbitTemplate.convertAndSend(HelloQueue.RABBIT_EXCHANGE.name(), HelloQueue.RABBIT_EXCHANGE.getRoutingKey(), customMessage);
        }catch (Exception ex){
            log.warn("send rabbit exchange fail: {}", ex.getMessage(), ex);
        }
        return customMessage;
    }
}
