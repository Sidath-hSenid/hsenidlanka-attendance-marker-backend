package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.AttendanceRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.AttendanceResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/attendances")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AttendanceRequest> addAttendance(@RequestBody AttendanceRequest attendanceRequest){
        return new ResponseEntity(attendanceService.addAttendance(attendanceRequest), HttpStatus.CREATED);
    }

    @GetMapping("/attendances")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AttendanceResponse>> allAttendances(){
        return new ResponseEntity(attendanceService.getAllAttendances(), HttpStatus.OK);
    }

    @GetMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceResponse> attendanceById(@PathVariable("id")  String id){
        return new ResponseEntity(attendanceService.getAttendanceById(id), HttpStatus.OK);
    }

    @DeleteMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteAttendance(@PathVariable("id")  String id){
        return new ResponseEntity(attendanceService.deleteAttendanceById(id), HttpStatus.OK);
    }

    @PutMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceRequest> updateAttendance(@PathVariable("id")  String id,@RequestBody AttendanceRequest attendanceRequest){
        return new ResponseEntity(attendanceService.updateAttendanceById(id, attendanceRequest), HttpStatus.OK);
    }
}
