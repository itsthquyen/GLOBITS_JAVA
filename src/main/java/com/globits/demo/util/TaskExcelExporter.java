package com.globits.demo.util;

import com.globits.demo.model.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskExcelExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Task> listTasks;

    public TaskExcelExporter(List<Task> listTasks) {
        this.listTasks = listTasks;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Tasks");
        Row row = sheet.createRow(0);
        
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        createCell(row, 0, "Project", style);
        createCell(row, 1, "Description", style);
        createCell(row, 2, "Start Time", style);
        createCell(row, 3, "End Time", style);
        createCell(row, 4, "Priority", style);
        createCell(row, 5, "Status", style);
        createCell(row, 6, "Person", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue(value != null ? value.toString() : "");
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Task task : listTasks) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            String projectName = task.getProject() != null ? task.getProject().getName() : "";
            createCell(row, columnCount++, projectName, style);
            
            createCell(row, columnCount++, task.getDescription(), style);
            
            String startTime = task.getStartTime() != null ? task.getStartTime().format(formatter) : "";
            createCell(row, columnCount++, startTime, style);
            
            String endTime = task.getEndTime() != null ? task.getEndTime().format(formatter) : "";
            createCell(row, columnCount++, endTime, style);
            
            String priorityStr = "";
            if(task.getPriority() != null) {
                switch(task.getPriority()) {
                    case 1: priorityStr = "Cao"; break;
                    case 2: priorityStr = "Trung bình"; break;
                    case 3: priorityStr = "Thấp"; break;
                    default: priorityStr = task.getPriority().toString();
                }
            }
            createCell(row, columnCount++, priorityStr, style);
            
            String statusStr = "";
            if(task.getStatus() != null) {
                switch(task.getStatus()) {
                    case 1: statusStr = "Mới tạo"; break;
                    case 2: statusStr = "Đang làm"; break;
                    case 3: statusStr = "Hoàn thành"; break;
                    case 4: statusStr = "Tạm hoãn"; break;
                    default: statusStr = task.getStatus().toString();
                }
            }
            createCell(row, columnCount++, statusStr, style);
            
            String personName = task.getPerson() != null ? task.getPerson().getFullName() : "";
            createCell(row, columnCount++, personName, style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
