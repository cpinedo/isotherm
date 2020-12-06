package com.marespinos.isotherm.framework;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CommandExecutor {
    private final Map<Class<?>, Handler<?, ?>> handlers;

    public CommandExecutor(Map<Class<?>, Handler<?, ?>> handlers) {
        this.handlers = handlers;
    }

    public CompletableFuture<?> executeCommand(Request command) {
        Handler<? extends Request, ?> handler = handlers.get(command.getClass());
        try {
            return handler.handle(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
