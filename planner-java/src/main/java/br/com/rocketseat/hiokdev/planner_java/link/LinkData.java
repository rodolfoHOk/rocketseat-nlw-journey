package br.com.rocketseat.hiokdev.planner_java.link;

import java.util.UUID;

public record LinkData(
        UUID id,
        String title,
        String url
) {
}
