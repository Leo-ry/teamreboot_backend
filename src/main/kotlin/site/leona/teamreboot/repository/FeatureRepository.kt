package site.leona.teamreboot.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.leona.teamreboot.entity.Feature

interface FeatureRepository : JpaRepository<Feature, Long>