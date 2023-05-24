package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.AttendanceRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.AttendanceResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;

import java.util.List;

public interface AttendanceService {
    AttendanceRequest addAttendance(AttendanceRequest attendanceRequest);

    List<AttendanceResponse> getAllAttendances();

    AttendanceResponse getAttendanceById(String id);

    AttendanceResponse getAttendanceByUserIdAndDate(String id, String date);

    List<AttendanceResponse> getAttendanceByUserId(String id);

    MessageResponse deleteAttendanceById(String id);

    AttendanceRequest updateAttendanceById(String id, AttendanceRequest attendanceRequest);

    AttendanceRequest updateAttendanceEndTimeById(String id, AttendanceRequest attendanceRequest);
}
