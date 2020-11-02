package com.nvn41091.repository;

import com.nvn41091.domain.ProjectInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectInformationRepository extends JpaRepository<ProjectInformation, Long> {
}
