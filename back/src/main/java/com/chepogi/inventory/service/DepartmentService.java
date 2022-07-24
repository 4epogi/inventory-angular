package com.chepogi.inventory.service;

import com.chepogi.inventory.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    public DepartmentDto save(DepartmentDto departmentDto);

    public List<DepartmentDto> getAllDepartmentsDto();

    public DepartmentDto getDepartmentById(Long id);

    public String deleteDepartment(Long id);

    public String updateDepartment(DepartmentDto departmentDto);

    public List<DepartmentDto> getDepartments(String name, int page, int size);
}
