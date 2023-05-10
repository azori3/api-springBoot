package com.example.application.Invoice;


import com.example.application.ExcelHelper.InvoiceexcelHelper;
import com.example.application.Exception.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path="/api/v1/admin/")
@CrossOrigin("http://localhost:8100/")
@RestController()
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        System.out.println("tester");
        if (InvoiceexcelHelper.hasExcelFormat(file)) {
            try {
                invoiceService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/list")
    @CrossOrigin("http://localhost:8100")
    public ResponseEntity<ResponseMessage> getAllInvoices()
    {
        var invoices = invoiceService.GetAllInvoice();
        System.out.println(invoices);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoices.toString()));
    }




}
