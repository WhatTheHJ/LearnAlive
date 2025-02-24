package com.korea.attendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.korea.attendance.model.Student;

@Mapper
public interface StudentMapper {
    @Insert("""
        INSERT INTO Student (student_id, university, department, name, email, class_id)
        VALUES (#{studentId}, #{university}, #{department}, #{name}, #{email}, #{classId})
    """)
    void insertStudent(Student newStudent);
    
 // ✅ 특정 강의실에 속한 모든 학생 조회
    @Select("""
    	    SELECT student_id, university, department, name, email, class_id 
    	    FROM Student 
    	    WHERE class_id = #{classId}
    	""")
    	@Results({
    	    @Result(column = "student_id", property = "studentId"),
    	    @Result(column = "university", property = "university"),
    	    @Result(column = "department", property = "department"),
    	    @Result(column = "name", property = "name"),
    	    @Result(column = "email", property = "email"),
    	    @Result(column = "class_id", property = "classId")
    	})
    	List<Student> findStudentsByClass(@Param("classId") int classId);  // ✅ @Param 추가

    
    @Update("""
    	    UPDATE Student 
    	    SET university = #{university}, department = #{department}, 
    	        name = #{name}, email = #{email}, class_id = #{classId}
    	    WHERE student_id = #{studentId}
    	""")
    	void updateStudent(
    	    @Param("studentId") String studentId,
    	    @Param("university") String university,
    	    @Param("department") String department,
    	    @Param("name") String name,
    	    @Param("email") String email,
    	    @Param("classId") int classId
    	);

    @Delete("""
    	    DELETE FROM Student WHERE student_id = #{studentId}
    	""")
    	void deleteStudent(@Param("studentId") String studentId);

}
