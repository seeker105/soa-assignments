package com.cooksys.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.repository.ProjectManagerRepository;

@Service
public class ProjectManagerService {

	private ProjectManagerRepository repo;
	private ProjectManagerMapper mapper;

	public ProjectManagerService(ProjectManagerRepository repo, ProjectManagerMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	public List<ProjectManagerDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public boolean has(Long id) {
		return repo.exists(id);
	}

	public ProjectManagerDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectManagerDto projectManagerDto) {
		projectManagerDto.setId(null);
		return repo.save(mapper.toEntity(projectManagerDto)).getId();
	}

	public void put(Long id, ProjectManagerDto projectManagerDto) {
		mustExist(id);
		projectManagerDto.setId(id);
		repo.save(mapper.toEntity(projectManagerDto));
	}
	
	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(ProjectManager.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.delete(id);
	}
}
