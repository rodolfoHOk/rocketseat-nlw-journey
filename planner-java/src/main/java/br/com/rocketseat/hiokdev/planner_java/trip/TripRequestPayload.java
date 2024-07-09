package br.com.rocketseat.hiokdev.planner_java.trip;

import java.util.List;

public record TripRequestPayload(
        String destination,
        String starts_at,
        String ends_at,
        String owner_email,
        String owner_name,
        List<String> emails_to_invite
) {
}
