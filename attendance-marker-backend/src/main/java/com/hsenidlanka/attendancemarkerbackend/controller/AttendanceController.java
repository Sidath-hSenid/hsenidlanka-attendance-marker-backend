package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.AttendanceRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.AttendanceResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    /**
     * Add attendances
    **/
    @PostMapping("/attendances")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AttendanceRequest> addAttendance(@RequestBody AttendanceRequest attendanceRequest){
        logger.info("AttendanceController - addAttendance()");
        return new ResponseEntity(attendanceService.addAttendance(attendanceRequest), HttpStatus.CREATED);
    }

     /**
     * Retrieve all attendances
     **/
    @GetMapping("/attendances")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AttendanceResponse>> allAttendances(){
        logger.info("AttendanceController - allAttendances()");
        return new ResponseEntity(attendanceService.getAllAttendances(), HttpStatus.OK);
    }

     /**
     * Retrieve an attendance by attendance ID
     **/
    @GetMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceResponse> attendanceById(@PathVariable("id")  String id){
        logger.info("AttendanceController - attendanceById()");
        return new ResponseEntity(attendanceService.getAttendanceById(id), HttpStatus.OK);
    }

     /**
     * Retrieve an attendance by user ID and Date
     **/
    @GetMapping("/attendances/user-id-date/{id}/{date}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceResponse> attendanceByUserIdAndDate(@PathVariable("id")  String id, @PathVariable("date")  String date){
        logger.info("AttendanceController - attendanceByUserIdAndDate()");
        return new ResponseEntity(attendanceService.getAttendanceByUserIdAndDate(id,date), HttpStatus.OK);
    }

     /**
     * Retrieve an attendance by user ID
     **/
    @GetMapping("/attendances/user-id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceResponse> getAttendanceByUserId(@PathVariable("id")  String id){
        logger.info("AttendanceController - getAttendanceByUserId()");
        return new ResponseEntity(attendanceService.getAttendanceByUserId(id), HttpStatus.OK);
    }

     /**
     * Delete an attendance by attendance ID
     **/
    @DeleteMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteAttendance(@PathVariable("id")  String id){
        logger.info("AttendanceController - deleteAttendance()");
        return new ResponseEntity(attendanceService.deleteAttendanceById(id), HttpStatus.OK);
    }

     /**
     * Update an attendance by attendance ID
     **/
    @PutMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceRequest> updateAttendance(@PathVariable("id")  String id,@RequestBody AttendanceRequest attendanceRequest){
        logger.info("AttendanceController - updateAttendance()");
        return new ResponseEntity(attendanceService.updateAttendanceById(id, attendanceRequest), HttpStatus.OK);
    }

     /**
     * Update an attendance end time by user ID
     **/
    @PutMapping("/attendances/update-end-time/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<AttendanceRequest> updateAttendanceEndTime(@PathVariable("id")  String id,@RequestBody AttendanceRequest attendanceRequest){
        logger.info("AttendanceController - updateAttendanceEndTime()");
        return new ResponseEntity(attendanceService.updateAttendanceEndTimeById(id, attendanceRequest), HttpStatus.OK);
    }
}
