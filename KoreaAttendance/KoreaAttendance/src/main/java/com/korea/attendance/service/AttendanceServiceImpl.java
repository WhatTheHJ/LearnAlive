package com.korea.attendance.service;

import com.korea.attendance.model.Attendance;
import com.korea.attendance.repository.AttendanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceImpl(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    // ✅ 특정 날짜 출석 데이터 조회
    @Override
    public List<Attendance> getAttendanceByClassAndDate(int classId, String date) {
        return attendanceMapper.findAttendanceByClassAndDate(classId, date);
    }


    // ✅ 출석 상태 변경
    @Override
    @Transactional
    public void updateAttendanceState(int attendanceId, String state) {
        attendanceMapper.updateAttendanceState(attendanceId, state);
    }

    // ✅ 출석 사유 변경
    @Override
    @Transactional
    public void updateAttendanceReason(int attendanceId, String reason) {
        attendanceMapper.updateAttendanceReason(attendanceId, reason);
    }

    // ✅ 출석 기록 추가 (중복 검사)
    @Override
    @Transactional
    public void addAttendance(Attendance attendance) {
        int existingCount = attendanceMapper.checkDuplicateAttendance(
                attendance.getStudentId(), attendance.getClassId(), attendance.getDate());

        if (existingCount == 0) { // 기존 기록이 없는 경우만 추가
            attendanceMapper.insertAttendance(
                    attendance.getStudentId(),
                    attendance.getClassId(),
                    attendance.getDate(),
                    attendance.getState());
        }
    }

    // ✅ 출석 기록 삭제
    @Override
    @Transactional
    public void deleteAttendance(int attendanceId) {
        attendanceMapper.deleteAttendance(attendanceId);
    }
    
    //학생 출석 기록
    @Override
    @Transactional
    public String studentCheckIn(Attendance request) {
        int count = attendanceMapper.checkExistingAttendance(request.getStudentId(), request.getClassId(), request.getDate());

        if (count > 0) {
            return "이미 출석한 기록이 있습니다.";
        }

        attendanceMapper.studentCheckIn(request.getStudentId(), request.getClassId(), request.getDate());
        return "출석 완료";
    }
}
