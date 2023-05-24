package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.AttendanceRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.AttendanceResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.model.Attendance;
import com.hsenidlanka.attendancemarkerbackend.repository.AttendanceRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public AttendanceRequest addAttendance(AttendanceRequest attendanceRequest) {
        logger.info("AttendanceServiceImpl - addAttendance()");
        try {
            attendanceRepository.save(modelMapper.map(attendanceRequest, Attendance.class));
            logger.info("AttendanceServiceImpl - addAttendance(Saving successful)");
            return attendanceRequest;
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve all attendances
     **/
    public List<AttendanceResponse> getAllAttendances() {
        try {
            logger.info("AttendanceServiceImpl - getAllAttendances()");
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if (attendanceList.isEmpty()) {
                logger.error("AttendanceServiceImpl - getAllAttendances(No attendances available.)");
                throw new HandleException("Exits without displaying data.");
            } else {
                logger.info("AttendanceServiceImpl - getAllAttendances(Attendances are available)");
                return modelMapper.map(attendanceList, new TypeToken<List<AttendanceResponse>>() {
                }.getType());
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve attendance by attendance ID
     **/
    public AttendanceResponse getAttendanceById(String id) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - getAttendanceById(Attendance available.)");
                Attendance attendance = attendanceObj.get();
                return modelMapper.map(attendance, AttendanceResponse.class);
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceById(No attendance available.)");
                throw new HandleException("Exits without displaying data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve attendance by user ID and Date
     **/
    public AttendanceResponse getAttendanceByUserIdAndDate(String id, String date) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceByUserIdAndDate()");
            List<Attendance> attendanceObjList = attendanceRepository.findAll();
            final AttendanceResponse attendanceObj = new AttendanceResponse();
            if (!attendanceObjList.isEmpty()) {
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
                return attendanceObj;
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceByUserIdAndDate(No attendance available with the ID.)");
                throw new HandleException("Exits without displaying data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve attendance by user ID
     **/
    public List<AttendanceResponse> getAttendanceByUserId(String id) {
        try {
            logger.info("AttendanceServiceImpl - getAttendanceByUserId()");
            List<Attendance> attendanceObjList = attendanceRepository.findAll();
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
                return attendanceResponseList;
            } else {
                logger.error("AttendanceServiceImpl - getAttendanceByUserId(No attendance available with theID.)");
                throw new HandleException("Exits without displaying data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Update attendance by attendance ID
     **/
    public AttendanceRequest updateAttendanceById(String id, AttendanceRequest attendanceRequest) {
        try {
            logger.info("AttendanceServiceImpl - updateAttendanceById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - updateAttendanceById(An attendance available with the ID.)");
                Attendance attendance = attendanceObj.get();
                attendance.setDate(attendanceRequest.getDate());
                attendance.setStartTime(attendanceRequest.getStartTime());
                attendance.setEndTime(attendanceRequest.getEndTime());
                attendance.setWorkedHours(attendanceRequest.getWorkedHours());
                attendance.setUser(attendanceRequest.getUser());
                attendanceRepository.save(modelMapper.map(attendance, Attendance.class));
                return attendanceRequest;
            } else {
                logger.error("AttendanceServiceImpl - updateAttendanceById(No attendance available with the ID.)");
                throw new HandleException("Exits without updating data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Update attendance end date by attendance ID
     **/
    public AttendanceRequest updateAttendanceEndTimeById(String id, AttendanceRequest attendanceRequest) {
        try {
            logger.info("AttendanceServiceImpl - updateAttendanceEndTimeById()");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if (attendanceObj.isPresent()) {
                logger.info("AttendanceServiceImpl - updateAttendanceById(An attendance available with the ID)");
                Attendance attendance = attendanceObj.get();
                attendance.setEndTime(attendanceRequest.getEndTime());
                attendance.setWorkedHours(attendanceRequest.getWorkedHours());
                attendance.setHalfDay(attendanceRequest.getHalfDay());
                attendanceRepository.save(modelMapper.map(attendance, Attendance.class));
                return attendanceRequest;
            } else {
                logger.error("AttendanceServiceImpl - updateAttendanceById(No attendance available with the ID)");
                throw new HandleException("Exits without updating data");
            }
        } catch (Exception exception) {
            throw exception;
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
                throw new HandleException("Exits without deleting data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }
}
