package com.goodcare.server.global.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private Status status;

    public Body getBody() {
        return this.status.getBody();
    }
}
