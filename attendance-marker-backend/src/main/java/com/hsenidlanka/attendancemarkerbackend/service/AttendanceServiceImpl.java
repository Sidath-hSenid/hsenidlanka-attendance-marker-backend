package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
import com.hsenidlanka.attendancemarkerbackend.model.Attendance;
import com.hsenidlanka.attendancemarkerbackend.repository.AttendanceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    /**
     * Add new attendance
     **/
    @Override
    public PostAttendanceRequest addAttendance(AttendanceRequest attendanceRequest) {
        logger.info("AttendanceServiceImpl - addAttendance()");
        try {
            attendanceRepository.save(modelMapper.map(attendanceRequest, Attendance.class));
            logger.info("AttendanceServiceImpl - addAttendance(Saving successful)");
            return new PostAttendanceRequest(attendanceRequest, 201);
        } catch (Exception exception) {
            logger.error("AttendanceServiceImpl - addAttendance()" + exception);
            return new PostAttendanceRequest(null, 400);
        }
    }

    /**
     * Retrieve all attendances
     **/
    public GetAttendanceResponseList getAllAttendances() {
        try {
            logger.info("AttendanceServiceImpl - getAllAttendances()");
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if (attendanceList.isEmpty()) {
                logger.error("AttendanceServiceImpl - getAllAttendances(No attendances available.)");
                return new GetAttendanceResponseList(null, 404);
            } else {
                logger.info("AttendanceServiceImpl - getAllAttendances(Attendances are available)");
                return new GetAttendanceResponseList(modelMapper.map(attendanceList, new TypeToken<List<AttendanceResponse>>() {
                }.getType()), 200);
            }
        } catch (Exception exception) {
            return new GetAttendanceResponseList(null, 400);
        }
    }

    /**
     * Retrieve attendance by attendance ID
     **/
    public GetAttendanceResponse getAttendanceById(String id) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - getAttendanceById(Attendance available.)");
                Attendance attendance = attendanceObj.get();
                return new GetAttendanceResponse(modelMapper.map(attendance, AttendanceResponse.class),200);
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceById(No attendance available.)");
                return new GetAttendanceResponse(null,404);
            }
        } catch (Exception exception) {
            return new GetAttendanceResponse(null,404);
        }
    }

    /**
     * Retrieve attendance by user ID and Date
     **/
    public GetAttendanceResponse getAttendanceByUserIdAndDate(String id, String date) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceByUserIdAndDate()");
            List<Attendance> attendanceObjList = attendanceRepository.findAll();
            final AttendanceResponse attendanceObj = new AttendanceResponse();
            if(!attendanceObjList.isEmpty()) {
                logger.info("AttendanceServiceImpl - getAttendanceByUserIdAndDate(attendanceObjList is not empty.)");
                attendanceObjList.forEach(attendance -> {
                    if (attendance.getDate().equals(date) && attendance.getUser().getId().equals(id)) {
                        logger.info("AttendanceServiceImpl - getAttendanceByUserIdAndDate(An attendance available with date for this user.)");
                        attendanceObj.setId(attendance.getId());
                        attendanceObj.setDate(attendance.getDate());
                        attendanceObj.setStartTime(attendance.getStartTime());
                        attendanceObj.setEndTime(attendance.getEndTime());
                        attendanceObj.setWorkedHours(attendance.getWorkedHours());
                        attendanceObj.setHalfDay(attendance.getHalfDay());
                    }
                });
                return new GetAttendanceResponse(attendanceObj, 200);
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceByUserIdAndDate(No attendance available with the user ID and Date.)");
                return new GetAttendanceResponse(null, 404);
            }
        } catch (Exception exception) {
            return new GetAttendanceResponse(null, 400);
        }
    }

    /**
     * Retrieve attendance by user ID
     **/
    public GetAttendanceResponseList getAttendanceByUserId(String id) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceByUserId()");
            Attendance attendanceObj = new Attendance();
            List<Attendance> attendanceObjList = attendanceRepository.findByOrderByDateDesc();
            List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
            if (!attendanceObjList.isEmpty()) {
                logger.info("AttendanceServiceImpl - getAttendanceByUserId(AttendanceObjList not empty.)");
                attendanceObjList.stream().filter(attendance -> {
                    if (attendance.getUser().getId().equals(id)) {
                        logger.info("AttendanceServiceImpl - getAttendanceByUserId(An attendance available with the user.)");
                        AttendanceResponse attendanceResponse = new AttendanceResponse();
                        attendanceResponse.setId(attendance.getId());
                        attendanceResponse.setDate(attendance.getDate());
                        attendanceResponse.setStartTime(attendance.getStartTime());
                        attendanceResponse.setEndTime(attendance.getEndTime());
                        attendanceResponse.setWorkedHours(attendance.getWorkedHours());
                        attendanceResponse.setHalfDay(attendance.getHalfDay());
                        attendanceResponse.setUser(attendance.getUser());
                        attendanceResponseList.add(attendanceResponse);
                        return true;
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
                return new GetAttendanceResponseList(attendanceResponseList,200);
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceByUserId(No attendance available with theID.)");
                return new GetAttendanceResponseList(null,404);
            }
        } catch (Exception exception) {
            return new GetAttendanceResponseList(null,400);
        }
    }

    /**
     * Update attendance by attendance ID
     **/
    public PutAttendanceUpdateRequestById updateAttendanceById(String id, AttendanceUpdateRequestById attendanceUpdateRequestById) {
        try {
            logger.info("AttendanceServiceImpl - updateAttendanceById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - updateAttendanceById(An attendance available with the ID.)");
                Attendance attendance = attendanceObj.get();
                attendance.setStartTime(attendanceUpdateRequestById.getStartTime());
                attendance.setEndTime(attendanceUpdateRequestById.getEndTime());
                attendance.setWorkedHours(attendanceUpdateRequestById.getWorkedHours());
                attendance.setHalfDay(attendanceUpdateRequestById.getHalfDay());
                attendanceRepository.save(modelMapper.map(attendance, Attendance.class));
                return new PutAttendanceUpdateRequestById(attendanceUpdateRequestById, 200);
            } else {
                logger.error("AttendanceServiceImpl - updateAttendanceById(No attendance available with the ID.)");
                return new PutAttendanceUpdateRequestById(null, 404);
            }
        } catch (Exception exception) {
            return new PutAttendanceUpdateRequestById(null, 400);
        }
    }

    /**
     * Update attendance end date by attendance ID
     **/
    public PutAttendanceUpdateEndTimeRequest updateAttendanceEndTimeById(String id, AttendanceUpdateEndTimeRequest attendanceUpdateEndTimeRequest) {
        try {
            logger.info("AttendanceServiceImpl - updateAttendanceEndTimeById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - updateAttendanceById(An attendance available with the ID)");
                Attendance attendance = attendanceObj.get();
                attendance.setEndTime(attendanceUpdateEndTimeRequest.getEndTime());
                attendance.setWorkedHours(attendanceUpdateEndTimeRequest.getWorkedHours());
                attendance.setHalfDay(attendanceUpdateEndTimeRequest.getHalfDay());
                attendanceRepository.save(modelMapper.map(attendance, Attendance.class));
                return new PutAttendanceUpdateEndTimeRequest(attendanceUpdateEndTimeRequest,200);
            } else {
                logger.error("AttendanceServiceImpl - updateAttendanceById(No attendance available with the ID)");
                return new PutAttendanceUpdateEndTimeRequest(null,404);
            }
        } catch (Exception exception) {
            return new PutAttendanceUpdateEndTimeRequest(null,400);
        }
    }

    /**
     * Delete attendance by attendance ID
     **/
    public MessageResponse deleteAttendanceById(String id) {
        try {
            logger.info("AttendanceServiceImpl - deleteAttendanceById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - deleteAttendanceById(An attendance available with the ID)");
                attendanceRepository.deleteById(id);
                return new MessageResponse("Attendance deleted successfully!", 200);
            } else {
                logger.error("AttendanceServiceImpl - deleteAttendanceById(No attendance available with this ID)");
                return new MessageResponse(null, 404);
            }
        } catch (Exception exception) {
            return new MessageResponse(null, 400);
        }
    }
}
