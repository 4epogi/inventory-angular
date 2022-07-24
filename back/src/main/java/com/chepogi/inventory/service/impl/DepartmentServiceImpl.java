package com.chepogi.inventory.service.impl;

import com.chepogi.inventory.dto.DepartmentDto;
import com.chepogi.inventory.exceptions.DepartmentException;
import com.chepogi.inventory.model.Department;
import com.chepogi.inventory.repository.DepartmentRepository;
import com.chepogi.inventory.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto departmentDto) {
        if (departmentRepository.findByNameIgnoreCase(departmentDto.getName()).isPresent()) {
            throw new DepartmentException("We already have department with such name");
        }
        Department department = Department.builder()
                .name(departmentDto.getName())
                .deviceUsers(null)
                .build();
        Department departmentSaved = departmentRepository.save(department);
        departmentDto.setId(departmentSaved.getId());
        return departmentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartmentsDto() {
        return departmentRepository.findAll(Sort.by("name"))
                .stream()
                .map(this::mapDepartmnetToDepartmentDto)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            throw new DepartmentException("There is no such department");
        }
        return mapDepartmnetToDepartmentDto(department.get());
    }

    @Override
    @Transactional
    public String deleteDepartment(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            departmentRepository.deleteById(id);
            return "Department was successfully deleted";
        } else {
            throw new DepartmentException("There is no such department with such id");
        }
    }

    @Override
    @Transactional
    public String updateDepartment(DepartmentDto departmentDto) {
        Optional<Department> duplicateDepartment = departmentRepository.findByNameIgnoreCase(departmentDto.getName());
        if(duplicateDepartment.isPresent()){
            return "there is a department with such name";
        }
        Optional<Department> departmentChanging = departmentRepository.findById(departmentDto.getId());
        if(departmentChanging.isPresent()){
            Department department = departmentChanging.get();
            department.setName(departmentDto.getName());
            Department updatedDepartment = departmentRepository.save(department);
            return "Department has been successfully updated";
        }
        throw new DepartmentException("Error while updating");
    }

    @Override
    public List<DepartmentDto> getDepartments(String name, int page, int size) {
        Page<Department> departments = departmentRepository.findByNameContaining(name, PageRequest.of(page, size));
        if (departments.hasContent()) {
            return departments.getContent()
                    .stream()
                .map(this::mapDepartmnetToDepartmentDto)
                .collect(toList());
        }
        return Collections.emptyList();
    }

    public DepartmentDto mapDepartmnetToDepartmentDto(Department department){
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
