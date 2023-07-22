package com.submarine.submarineBase.SubmarineBase.service;

import com.submarine.submarineBase.SubmarineBase.controller.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class MessageHandler {

    private final List<Consumer<MessageDTO>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<MessageDTO> listener) {
        listeners.add(listener);
    }

    public void publish(MessageDTO liveScore) {
        listeners.forEach(listener -> listener.accept(liveScore));
    }
}
