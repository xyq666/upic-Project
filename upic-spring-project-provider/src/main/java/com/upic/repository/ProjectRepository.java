package com.upic.repository;

import com.upic.enums.RankEnum;
import com.upic.po.Project;

import com.upic.repository.spec.ProjectSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by zhubuqing on 2017/9/7.
 */
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    public Project findByProjectNum(String projectNum);

    @Query(value = "SELECT project FROM Project project where project.rankEnum=?1 and project.unit=?2 and project.implementationProcess ='AUDITED' or project.implementationProcess= 'HAVE_IN_HAND'")
    public Page<Project> getOnlineProject(RankEnum rankEnum, String unit, Pageable page);

    @Query(value = "select project from Project project where project.signUpStartTime < ?1 and project.signUpEndTime > ?1 and project.implementationProcess = 'ENROLLMENT'")
    Page<Project> getProjectWithoutSignUp(Date now, Pageable pageable);

    @Query(value = "select project from Project project where project.guidanceNum = ?1 and (project.projectNum like %?2% or project.projectName like %?2% or project.projectCategory like %?2%)")
    Page<Project> projectSearchBar(String userNum, String keyword, Pageable pageable);

    @Query(value = "select project from Project project where project.projectNum like %?1% or project.projectName like %?1% or project.guidanceMan like %?1%")
    Page<Project> projectSearchBar(String keyword, Pageable pageable);

    List<Project> getByGuidanceNum(String guidanceNum);

    Page<Project> getProjectByGuidanceNum(String guidanceNum, Pageable pageable);

    @Query(value = "select project from Project project where project.guidanceNum = ?1")
    List<Project> exportProjectByGuidanceNum(String guidanceNum);

    @Query(value = "select project from Project project where project.guidanceNum = ?1 and (project.projectNum like %?2% or project.projectName like %?2% or project.guidanceMan like %?2%)")
    List<Project> exportProjectSearchBar(String userNum, String keyword);

    String deleteByProjectNum(String projectNum);
}