package org.madi.lab8.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.madi.lab8.domain.Matrix;
import org.madi.lab8.service.MatrixService;
import org.madi.lab8.utils.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class MatrixController {
    @Autowired
    private MatrixService matrixService;

    @GetMapping("/calculator")
    public String showCalculator(Model model) {
        String jsonMatrix1 = "";
        String jsonMatrix2 = "";
        model.addAttribute("jsonMatrix1", jsonMatrix1);
        model.addAttribute("jsonMatrix2", jsonMatrix2);
        model.addAttribute("properties", new Properties());
        return "index";
    }

    @PostMapping("/multiply")
    public String calculate(@ModelAttribute("jsonMatrix1") String jsonMatrix1,
                            @ModelAttribute("jsonMatrix2") String jsonMatrix2,
                            @ModelAttribute("properties") Properties properties,
                            Model model) throws JsonProcessingException {
        int[][] matrix1FromJson = matrixService.getMatrixFromJson(jsonMatrix1);
        Matrix matrix1 = new Matrix(matrix1FromJson.length, matrix1FromJson[0].length);
        matrixService.convertJsonToMatrix(matrix1FromJson, matrix1);
        int[][] matrix2FromJson = matrixService.getMatrixFromJson(jsonMatrix2);
        Matrix matrix2 = new Matrix(matrix2FromJson.length, matrix2FromJson[0].length);
        matrixService.convertJsonToMatrix(matrix2FromJson, matrix2);

        long startTime = System.currentTimeMillis();
        Matrix result = matrixService.multiply(matrix1, matrix2, properties.getThreadsCnt());
        long endTime = System.currentTimeMillis();
        model.addAttribute("jsonMatrix1", jsonMatrix1);
        model.addAttribute("jsonMatrix2", jsonMatrix2);
        model.addAttribute("result", result);
        model.addAttribute("matrixSize", result.getMatrix().length);
        model.addAttribute("timeTaken", endTime - startTime);
        return "index";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("properties", new Properties());
        return "index2";
    }

    @PostMapping("/receiveResult")
    public String receiveResults(@ModelAttribute("properties") Properties properties,
                                 Model model) throws IOException {
        System.out.println("Generating...");
        Matrix matrix1 = matrixService.generateRandomMatrix(properties.getMatrixSize(), properties.getMatrixSize());
        Matrix matrix2 = matrixService.generateRandomMatrix(properties.getMatrixSize(), properties.getMatrixSize());

        System.out.println("Multiplying...");
        long startTime = System.currentTimeMillis();
        Matrix result = matrixService.multiply(matrix1, matrix2, properties.getThreadsCnt());
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        model.addAttribute("timeTaken", endTime - startTime);
        model.addAttribute("result", result);
        return "index2";
    }
}