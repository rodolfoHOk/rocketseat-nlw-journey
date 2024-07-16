package br.com.rocketseat.hiokdev.planner_java.domain.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    List<Activity> findByTripId(UUID tripId);

}
