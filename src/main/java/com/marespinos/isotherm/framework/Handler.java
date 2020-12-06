package com.marespinos.isotherm.framework;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface Handler<T extends Request, R> {
    CompletableFuture<R> handle(Request command) throws IOException;
}
