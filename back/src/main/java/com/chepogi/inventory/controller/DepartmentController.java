package com.chepogi.inventory.controller;

import com.chepogi.inventory.dto.DepartmentDto;
import com.chepogi.inventory.dto.HttpResponse;
import com.chepogi.inventory.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<HttpResponse> createDepartment(@RequestBody DepartmentDto departmentDto){
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .status(HttpStatus.CREATED)
                .data(departmentService.save(departmentDto))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpResponse> updateDepartment(@RequestBody DepartmentDto departmentDto){
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .status(HttpStatus.OK)
                .data(departmentService.updateDepartment(departmentDto))
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllDepartments(){
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .status(HttpStatus.OK)
                .data(departmentService.getAllDepartmentsDto())
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<List<DepartmentDto>> getAllDepartmentsPages(@RequestParam Optional<String> name,
                                                                      @RequestParam Optional<Integer> page,
                                                                      @RequestParam Optional<Integer> size){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentService.getDepartments(name.orElse(""), page.orElse(0), size.orElse(10)));
    }



    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getDepartmentById(@PathVariable Long id){
        HttpResponse response = HttpResponse.builder().timeStamp(LocalTime.now().toString())
                .status(HttpStatus.OK)
                .data(departmentService.getDepartmentById(id)).build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteDepartmentBy(@PathVariable Long id){
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .status(HttpStatus.OK)
                .data(departmentService.deleteDepartment(id))
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
