package com.example.application.Invoice;


import com.example.application.ExcelHelper.InvoiceexcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class InvoiceService {


    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public void save(MultipartFile file) {
        try {
            List<Invoice> invoices = InvoiceexcelHelper.excelToInvoices(file.getInputStream());
            log.info("Save Invoices {} time Elapsed: {}", invoices.size(), invoices.toString());
            repository.saveAll(invoices);
            log.info("tester");
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Invoice> GetAllInvoice()
    {

        return  repository.findAll();

    }
}
