package com.korea.attendance.repository;

import com.korea.attendance.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    @Select("SELECT student_id AS userId, name, 'student' AS role FROM Student WHERE student_id = #{userId} " +
            "UNION " +
            "SELECT prof_id AS userId, name, 'professor' AS role FROM Professor WHERE prof_id = #{userId}")
    Optional<User> findUserById(String userId);
}
