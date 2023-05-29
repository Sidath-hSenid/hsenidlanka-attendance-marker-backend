package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
import com.hsenidlanka.attendancemarkerbackend.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    /**
     * Add attendances
    **/
    @PostMapping("/attendances")
    @PreAuthorize("hasRole('USER')")
    public PostAttendanceRequest addAttendance(@RequestBody AttendanceRequest attendanceRequest){
        logger.info("AttendanceController - addAttendance()");
        return attendanceService.addAttendance(attendanceRequest);
    }

     /**
     * Retrieve all attendances
     **/
    @GetMapping("/attendances")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetAttendanceResponseList allAttendances(){
        logger.info("AttendanceController - allAttendances()");
        return attendanceService.getAllAttendances();
    }

     /**
     * Retrieve an attendance by attendance ID
     **/
    @GetMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetAttendanceResponse attendanceById(@PathVariable("id")  String id){
        logger.info("AttendanceController - attendanceById()");
        return attendanceService.getAttendanceById(id);
    }

     /**
     * Retrieve an attendance by user ID and Date
     **/
    @GetMapping("/attendances/user-id-date/{id}/{date}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetAttendanceResponse attendanceByUserIdAndDate(@PathVariable("id")  String id, @PathVariable("date")  String date){
        logger.info("AttendanceController - attendanceByUserIdAndDate()");
        return attendanceService.getAttendanceByUserIdAndDate(id,date);
    }

     /**
     * Retrieve an attendance by user ID
     **/
    @GetMapping("/attendances/user-id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetAttendanceResponseList getAttendanceByUserId(@PathVariable("id")  String id){
        logger.info("AttendanceController - getAttendanceByUserId()");
        return attendanceService.getAttendanceByUserId(id);
    }

     /**
     * Delete an attendance by attendance ID
     **/
    @DeleteMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public MessageResponse deleteAttendance(@PathVariable("id")  String id){
        logger.info("AttendanceController - deleteAttendance()");
        return attendanceService.deleteAttendanceById(id);
    }

     /**
     * Update an attendance by attendance ID
     **/
    @PutMapping("/attendances/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public PutAttendanceUpdateRequestById updateAttendance(@PathVariable("id")  String id, @RequestBody AttendanceUpdateRequestById attendanceUpdateRequestById){
        logger.info("AttendanceController - updateAttendance()");
        return attendanceService.updateAttendanceById(id, attendanceUpdateRequestById);
    }

     /**
     * Update an attendance end time by user ID
     **/
    @PutMapping("/attendances/update-end-time/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public PutAttendanceUpdateEndTimeRequest updateAttendanceEndTime(@PathVariable("id")  String id, @RequestBody AttendanceUpdateEndTimeRequest attendanceUpdateEndTimeRequest){
        logger.info("AttendanceController - updateAttendanceEndTime()");
        return attendanceService.updateAttendanceEndTimeById(id, attendanceUpdateEndTimeRequest);
    }
}
