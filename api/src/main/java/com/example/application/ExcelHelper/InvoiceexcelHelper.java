package com.example.application.ExcelHelper;

import com.example.application.Invoice.Invoice;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InvoiceexcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "listes acomptes medecins";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Invoice> excelToInvoices(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Invoice> invoices = new ArrayList<Invoice>();
            System.out.println(rows);
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Invoice invoice = new Invoice();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            invoice.setIdFacture(currentCell.getStringCellValue());
                            break;

                        case 1:
                            invoice.setFacture(currentCell.getStringCellValue());
                            break;

                        case 2:
                            invoice.setType(currentCell.getStringCellValue());
                            break;
                        case 3:
                            invoice.setDateFacture(String.valueOf(currentCell.getStringCellValue()));
                            break;

                        case 4:
                            invoice.setDatePayment(String.valueOf(currentCell.getStringCellValue()));
                            break;

                        case 5:
                            invoice.setMode(currentCell.getStringCellValue());
                            break;
                        case 6:
                            invoice.setSoldFactorization(currentCell.getNumericCellValue());
                            break;
                        case 7:
                            invoice.setSoldPayment(currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                invoices.add(invoice);
            }

            workbook.close();

            return invoices;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
