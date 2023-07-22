package com.submarine.submarineBase.SubmarineBase.controller;


import com.submarine.submarineBase.SubmarineBase.controller.dto.MessageDTO;
import com.submarine.submarineBase.SubmarineBase.service.MessageHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class Messaging {

    private final MessageHandler handler;

    public Messaging(MessageHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/messaging")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MessageDTO> send(@RequestBody MessageDTO message) {
        handler.publish(message);
        return Mono.just(message);
    }

    @GetMapping(path = "/messaging", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> consumer() {
        return Flux.create(sink -> handler.subscribe(sink::next)).map(
                liveScore -> ServerSentEvent.builder().data(liveScore).event("message").build());
    }
}
