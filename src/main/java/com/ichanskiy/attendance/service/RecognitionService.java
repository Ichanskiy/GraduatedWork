package com.ichanskiy.attendance.service;

import com.ichanskiy.attendance.entity.Student;
import com.ichanskiy.attendance.repository.StudentRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static com.ichanskiy.attendance.service.RegistrationService.*;

@Service
public class RecognitionService {

    private static final Logger logger = Logger.getLogger(RecognitionService.class.getName());
    private static final String RESULT_FOLDER = "D:/Diplom/testing/recognition/";
    private static final String INPUT_FILE_PATH = "D:/Diplom/registration/user.jpg";
    private static final String SEPARATOR = ".";

    @Autowired
    private StudentRepository studentRepository;

    public void identification(Student student) {
        createPhoto();
        String fileName = renamePhotoWithStudentIdAndMoveFromToFolder(student, INPUT_FOLDER, RESULT_FOLDER);
        if (fileName != null) {
            student.setPhotoPath(RESULT_FOLDER.concat(fileName));
            studentRepository.save(student);
        }
        setAttendance(student);
//        removeStudentPhoto(student);
    }

    private void setAttendance(Student student) {
        try {
            String commandLine = String.format("python  C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\03_face_recognition.py %s %s", student.getId().toString(), student.getPhotoPath());
            Runtime.getRuntime().exec(commandLine, null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
    }

    private void removeStudentPhoto(Student student) {
        try {
            FileUtils.forceDelete(new File(student.getPhotoPath()));
        } catch (IOException e) {
            logger.info("Error delete file");
        }
    }
}
