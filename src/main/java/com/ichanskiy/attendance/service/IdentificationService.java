package com.ichanskiy.attendance.service;

import com.ichanskiy.attendance.entity.Attendance;
import com.ichanskiy.attendance.entity.Student;
import com.ichanskiy.attendance.repository.AttendanceRepository;
import com.ichanskiy.attendance.repository.StudentRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.ichanskiy.attendance.service.RegistrationService.*;

@Service
public class IdentificationService {

    private static final Logger logger = Logger.getLogger(IdentificationService.class.getName());
    private static final String RESULT_FOLDER = "D:/Diplom/testing/recognition/";
    private static final String INPUT_FILE_PATH = "D:/Diplom/registration/user.jpg";
    private static final String SEPARATOR = ".";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public boolean identificationStatusIsOk(Student student) {
        createPhoto();
        String fileName = renamePhotoWithStudentIdAndMoveFromToFolder(student, INPUT_FOLDER, RESULT_FOLDER);
        if (fileName != null) {
            student.setPhotoPath(RESULT_FOLDER.concat(fileName));
            studentRepository.save(student);
        }
        sleep(2);
        setAttendance(student);
        sleep(2);
//        removeStudentPhoto(student);
        List<Attendance> attendances = attendanceRepository.getAllByStudent(student);
        int countFailed = 0;
        for (Attendance attendance : attendances) {
            System.out.println(attendance.isPresence());
            if (!attendance.isPresence()){
                ++countFailed;
            }
        }
        return countFailed < 2;
    }

    private void setAttendance(Student student) {
        try {
            String commandLine = String.format("python  C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\03_face_recognition.py %s %s", student.getId().toString(), student.getPhotoPath());
            System.out.println(commandLine);
            Runtime.getRuntime().exec(commandLine, null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
    }
//    @Scheduled(cron = "0/30 * * * * ?")
//    private void setAttendance() {
//        try {
//            String commandLine = String.format("python  C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\03_face_recognition.py %s %s",
//                    "113", "D:/Diplom/testing/input/113.test.jpg");
//            System.out.println(commandLine);
//            Runtime.getRuntime().exec(commandLine, null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
//        } catch (IOException e) {
//            logger.info("Error create data set from images");
//        }
//    }

    private void removeStudentPhoto(Student student) {
        try {
            FileUtils.forceDelete(new File(student.getPhotoPath()));
        } catch (IOException e) {
            logger.info("Error delete file");
        }
    }
}
