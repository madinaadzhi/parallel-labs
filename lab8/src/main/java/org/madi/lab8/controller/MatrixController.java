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
    public String calculate(@RequestParam("jsonMatrix1") String jsonMatrix1,
                            @RequestParam("jsonMatrix2") String jsonMatrix2,
                            @ModelAttribute("properties") Properties properties,
                            Model model) throws JsonProcessingException {
        int[][] arr1FromJson = matrixService.convertIntArrToMatrix(jsonMatrix1);
        Matrix matrix1 = new Matrix(arr1FromJson.length, arr1FromJson[0].length);
        matrixService.convertIntArrToMatrix(arr1FromJson, matrix1);
        int[][] arr2FromJson = matrixService.convertIntArrToMatrix(jsonMatrix2);
        Matrix matrix2 = new Matrix(arr2FromJson.length, arr2FromJson[0].length);
        matrixService.convertIntArrToMatrix(arr2FromJson, matrix2);

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

    @PostMapping("/getResults")
    public String getResults(@ModelAttribute("properties") Properties properties,
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