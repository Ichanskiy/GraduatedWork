package com.ichanskiy.attendance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class AttendanceApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(AttendanceApplication.class, args);
//        Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\01_face_dataset.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\OpenCV-Face-Recognition\\FacialRecognition\\"));
//        System.out.println("test");
//        Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\02_face_training.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\OpenCV-Face-Recognition\\FacialRecognition\\"));
//        System.out.println("test");
//        Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\03_face_recognition.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\OpenCV-Face-Recognition\\FacialRecognition\\"));
    }

    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime().exec("python C:\\Users\\Ichanskiy\\PycharmProjects\\video_streaming_with_flask_example\\main.py", null, new File("C:\\Users\\Ichanskiy\\PycharmProjects\\FaceIdWebCam\\FacialRecognition\\"));
    }
}
