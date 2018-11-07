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
public class RegistrationService {

    private static final Logger logger = Logger.getLogger(RegistrationService.class.getName());
    private static final String REQUEST = "http://127.0.0.1:5000/video_feed";
    private static final String RESULT_FOLDER = "D:/Diplom/testing/input/";
    private static final String INPUT_FILE_PATH = "D:/Diplom/registration/user.jpg";
    private static final String DATA_SET_PATH = "D:\\Diplom\\testing\\dataset";
    private static final String SEPARATOR = ".";
    private static final String EXTENDS = ".jpg";
    static final String INPUT_FOLDER = "D:/Diplom/registration/";


    @Autowired
    private StudentRepository studentRepository;

    public Student registration(Student student) {
        createPhoto();
        studentRepository.save(student);
        renamePhotoWithStudentIdAndMoveFromToFolder(student, INPUT_FOLDER, RESULT_FOLDER);
        sleep(1);
        createDataSetFromPhoto();
        sleep(1);
        faceTraining();
        removePhoto(INPUT_FOLDER);
        removePhoto(RESULT_FOLDER);
        return successCheckTraining(student);
    }

    private Student successCheckTraining(Student student) {
        if(folderConstrainsStudentPhoto(student)){
            return student;
        } else {
            createPhoto();
            renamePhotoWithStudentIdAndMoveFromToFolder(student, INPUT_FOLDER, RESULT_FOLDER);
            sleep(1);
            createDataSetFromPhoto();
            sleep(1);
            faceTraining();
            if (folderConstrainsStudentPhoto(student)) {
                return student;
            } else {
                return null;
            }
        }
    }

    private boolean folderConstrainsStudentPhoto(Student student) {
        String[] extension = new String[]{"jpg"};
        Collection listFiles = FileUtils.listFiles(new File(DATA_SET_PATH), extension, false);
        for (Object file : listFiles) {
            if (file.toString().contains(student.getId().toString().concat("."))){
                return true;
            }
        }
        return false;
    }

    private void removePhoto(String folder) {
        String[] extension = new String[]{"jpg"};
        Collection listFiles = FileUtils.listFiles(new File(folder), extension, false);
        for (Object file : listFiles) {
            try {
                FileUtils.forceDelete(new File(file.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep(int seconds){
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void createPhoto() {
        HttpUriRequest request = new HttpGet(REQUEST);
        try {
            HttpClientBuilder.create().build().execute(request);
        } catch (IOException e) {
            logger.info("Error execute request");
        }
    }

    static String renamePhotoWithStudentIdAndMoveFromToFolder(Student student, String from, String to) {
        File resultFolder = new File(to);
        File inputFile = new File(INPUT_FILE_PATH);
        File renamedFile = new File(from
                .concat(student.getId().toString())
                .concat(SEPARATOR)
                .concat(student.getFirstName())
                .concat(EXTENDS));
        boolean successesRename = inputFile.renameTo(renamedFile);
        if (successesRename) {
            copyFileToDirectory(renamedFile, resultFolder);
            return renamedFile.getName();
        }
        return null;
    }

    private void createDataSetFromPhoto() {
        try {
            Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\01_face_dataset.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
    }

    private void faceTraining() {
        try {
            Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\02_face_training.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
        } catch (IOException e) {
            logger.info("Error create data set from images");
        }
    }

    private static void copyFileToDirectory(File inputFile, File resultFolder) {
        try {
            FileUtils.copyFileToDirectory(inputFile, resultFolder);
            FileUtils.forceDelete(inputFile);
        } catch (IOException e) {
            logger.info("Error move file");
        }
    }
}
