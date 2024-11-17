package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Set;

import static org.example.CommonPlayCalculationUtils.getAllCombinations;

public class Main {
    public static String FORMAT = "%.7f";

    public static void main(String[] args) {
        write();
    }

    private static void write() {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream("Times table.xlsx")) {
            for (int i = 1; i <= 12; i++) {
                Sheet sheet = workbook.createSheet(String.valueOf(i));
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("s");
                row.createCell(1).setCellValue("t_opt[s]");
                row.createCell(2).setCellValue("t_rand[s]");
                int rowNum = 1;

                for (Set<Integer> combination : getAllCombinations(i)) {
                    Row combinationRow = sheet.createRow(rowNum);
                    combinationRow.createCell(0).setCellValue(combination.toString());
                    combinationRow.createCell(1).setCellValue(getFormattedOptimalPositionTime(combination));
                    combinationRow.createCell(2).setCellValue(getFormattedRandomPositionTime(combination));
                    rowNum++;
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getFormattedOptimalPositionTime(Set<Integer> combination) {
        return Double.parseDouble(String.format(FORMAT, OptimalPlayCalculationUtils.calculatePositionTime(combination)).replace(",", "."));
    }

    private static double getFormattedRandomPositionTime(Set<Integer> combination) {
        return Double.parseDouble(String.format(FORMAT, RandomPlayCalculationUtils.calculatePositionTime(combination)).replace(",", "."));
    }
}