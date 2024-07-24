package br.com.rocketseat.hiokdev.planner_java.domain.common.gateway;

import lombok.Builder;

import java.util.Map;

public interface MailGateway {

    void send(MailMessage mail);

    record MailMessage(
            String destination,
            String subject,
            String body,
            Map<String, Object> variables
    ) {

        @Builder
        public MailMessage {}

    }

}
