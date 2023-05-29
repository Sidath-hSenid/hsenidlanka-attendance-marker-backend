package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetAttendanceResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetAttendanceResponseList;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;

public interface AttendanceService {
    PostAttendanceRequest addAttendance(AttendanceRequest attendanceRequest);

    GetAttendanceResponseList getAllAttendances();

    GetAttendanceResponse getAttendanceById(String id);

    GetAttendanceResponse getAttendanceByUserIdAndDate(String id, String date);

    GetAttendanceResponseList getAttendanceByUserId(String id);

    MessageResponse deleteAttendanceById(String id);

    PutAttendanceUpdateRequestById updateAttendanceById(String id, AttendanceUpdateRequestById attendanceUpdateRequestById);

    PutAttendanceUpdateEndTimeRequest updateAttendanceEndTimeById(String id, AttendanceUpdateEndTimeRequest attendanceUpdateEndTimeRequest);
}
