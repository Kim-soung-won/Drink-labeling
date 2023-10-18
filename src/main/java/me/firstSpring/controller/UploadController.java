//package me.firstSpring.controller;
//
//import lombok.RequiredArgsConstructor;
//import me.firstSpring.domain.User;
//import me.firstSpring.dto.User.UserViewResponse;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.tensorflow.keras.models.Model;
//import org.tensorflow.keras.models.loadmodel;
//import org.tensorflow.Tensor;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.core.MatOfFloat;
//import org.opencv.core.Size;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Base64;
//
//
//@RequiredArgsConstructor
//@RestController
//public class UploadController {
//
//    @PostMapping("/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            byte[] fileBytes = file.getBytes();
//            String base64Data = Base64.getEncoder().encodeToString(fileBytes);
//
//            // TensorFlow 모델 실행
//            float result = runModel(base64Data);
//
//            return "{\"prediction\": " + result + "}";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "{\"error\": \"An error occurred.\"}";
//        }
//    }
//
//    private float runModel(String base64Data) {
//        try {
//            // 모델 로드
//            Model model = loadModel("path_to_your_model.h5");
//
//            // 입력 데이터를 텐서로 변환
//            float[] inputData = prepareInputData(base64Data);
//
//            // 모델 실행
//            Tensor<?> inputTensor = Tensor.create(inputData);
//            Tensor<?> outputTensor = model.predict(inputTensor);
//
//            // 출력 데이터 처리
//            float[] outputData = new float[outputTensor.numElements()];
//            outputTensor.copyTo(outputData);
//
//            return outputData[0]; // 예측 결과 반환
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1; // 오류 발생 시 음수 값 반환
//        }
//    }
//
//    // 입력 데이터를 준비하는 메서드 (실제 데이터에 따라 다를 수 있습니다)
//    private float[] prepareInputData(String base64Data) {
//        try {
//            // Base64 문자열을 바이트 배열로 디코딩
//            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
//
//            // 바이트 배열을 InputStream으로 변환
//            InputStream inputStream = new ByteArrayInputStream(imageBytes);
//
//            // 이미지를 OpenCV Mat으로 읽어오기 (OpenCV 라이브러리 필요)
//            Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);
//
//            // 이미지 크기 조정 (224x224로 조정)
//            Imgproc.resize(image, image, new Size(224, 224));
//
//            // 이미지를 텐서로 변환
//            MatOfFloat mean = new MatOfFloat(123.68f, 116.779f, 103.939f);
//            Mat substracted = new Mat(image.size(), CvType.CV_32FC3);
//            Core.subtract(image, mean, substracted);
//            Mat floatImage = new Mat();
//            substracted.convertTo(floatImage, CvType.CV_32FC3);
//
//            // 텐서의 입력 데이터로 사용할 float 배열로 변환
//            float[] inputData = new float[(int) (floatImage.total() * floatImage.channels())];
//            floatImage.get(0, 0, inputData);
//
//            return inputData;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}