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

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public AttendanceRequest addAttendance(AttendanceRequest attendanceRequest) {
        try{
            attendanceRepository.save(modelMapper.map(attendanceRequest, Attendance.class));
            logger.info("Attendance added...");
            return attendanceRequest;
        }
        catch(Exception exception){
            throw exception;
        }
    }

    public List<AttendanceResponse> getAllAttendances(){
        try{
            logger.info("Inside getAllAttendances() function...");
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if(attendanceList.isEmpty()){
                logger.warn("No attendances available!");
                throw new HandleException("Exits without displaying data");
            }
            else{
                logger.info("Attendances are available..." + attendanceList);
                return modelMapper.map(attendanceList, new TypeToken<List<AttendanceResponse>>(){}.getType());
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public AttendanceResponse getAttendanceById(String id){
        try{
            logger.info("Inside getAttendanceById() function...");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if(attendanceObj.isPresent()){
                logger.info("A attendance available with this ID!" + attendanceObj);
                Attendance attendance = attendanceObj.get();
                return modelMapper.map(attendance, AttendanceResponse.class);
            }else{
                logger.warn("No attendance available with this ID!");
                throw new HandleException("Exits without displaying data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public AttendanceRequest updateAttendanceById(String id, AttendanceRequest attendanceRequest){
        try{
            logger.info("Inside updateAttendanceById() function...");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if(attendanceObj.isPresent()){
                logger.info("A attendance available with this ID!" + attendanceObj);
                Attendance attendance = attendanceObj.get();
                attendance.setDate(attendanceRequest.getDate());
                attendance.setStartTime(attendanceRequest.getStartTime());
                attendance.setEndTime(attendanceRequest.getEndTime());
                attendance.setWorkedHours(attendanceRequest.getWorkedHours());
                attendance.setUser(attendanceRequest.getUser());
                attendanceRepository.save(modelMapper.map(attendance, Attendance.class));
                logger.info("Attendance updated...");
                return attendanceRequest;
            }else{
                logger.warn("No attendance available with this ID!");
                throw new HandleException("Exits without updating data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public MessageResponse deleteAttendanceById(String id){
        try {
            logger.info("Inside deleteAttendanceById() function...");
            Optional<Attendance> attendanceObj = attendanceRepository.findById(id);
            if(attendanceObj.isPresent()){
                logger.warn("A attendance available with this ID!" + attendanceObj);
                attendanceRepository.deleteById(id);
                return new MessageResponse("Attendance deleted successfully!");
            }else{
                logger.warn("No attendance available with this ID!");
                throw new HandleException("Exits without deleting data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }
}
