package com.ichanskiy.attendance.service;

import com.ichanskiy.attendance.entity.Student;
import com.ichanskiy.attendance.repository.StudentRepository;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

@Service
public class StudentService {

    private static final Logger logger = Logger.getLogger(StudentService.class.getName());
    private static final String REQUEST = "http://127.0.0.1:5000/video_feed";
    private static final String RESULT_FOLDER = "D:/Diplom/testing/input/";
    private static final String INPUT_FOLDER = "D:/Diplom/registration/";
    private static final String INPUT_FILE_PATH = "D:/Diplom/registration/user.png";
    private static final String EXTENDS = ".png";
    private static final String SEPARATOR = ".";

    @Autowired
    private StudentRepository studentRepository;

    public Student registration(Student student) {
        createPhoto();
        studentRepository.save(student);
        renamePhotoWithStudentId(student);
        createDataSetFromPhoto();
        faceTrainingAndRemovePhoto();
        return student;
    }

    private void createPhoto() {
        HttpUriRequest request = new HttpGet(REQUEST);
        try {
            HttpClientBuilder.create().build().execute(request);
        } catch (IOException e) {
            logger.info("Error execute request");
        }
    }

    private void renamePhotoWithStudentId(Student student) {
        File resultFolder = new File(RESULT_FOLDER);
        File inputFile = new File(INPUT_FILE_PATH);
        File renamedFile = new File(INPUT_FOLDER
                .concat(student.getId().toString())
                .concat(SEPARATOR)
                .concat(student.getFirstName())
                .concat(EXTENDS));
        boolean successesRename = inputFile.renameTo(renamedFile);
        if (successesRename) {
            copyFileToDirectory(renamedFile, resultFolder);
        }
    }

    private void createDataSetFromPhoto() {
        try {
            Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\01_face_dataset.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
    }

    private void faceTrainingAndRemovePhoto() {
        try {
            Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\02_face_training.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
        String[] extension = new String[]{"png"};
        Collection listFiles = FileUtils.listFiles(new File(RESULT_FOLDER), extension, false);
        for (Object file : listFiles) {
            try {
                FileUtils.forceDelete(new File(file.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFileToDirectory(File inputFile, File resultFolder) {
        try {
            FileUtils.copyFileToDirectory(inputFile, resultFolder);
            FileUtils.forceDelete(inputFile);
        } catch (IOException e) {
            logger.info("Error move file");
        }
    }
}
