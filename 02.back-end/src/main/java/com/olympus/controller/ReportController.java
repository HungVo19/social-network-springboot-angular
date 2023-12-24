package com.olympus.controller;

import com.olympus.config.Constant;
import com.olympus.dto.response.BaseResponse;
import com.olympus.dto.response.report.WeeklyReportData;
import com.olympus.service.IReportService;
import com.olympus.utils.AppUtils;
import com.olympus.validator.AppValidator;
import com.olympus.validator.annotation.user.ExistUserById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/v1/{userId}/report")
@CrossOrigin("*")
@Tag(name = "Report", description = "Report Management APIs")
@Validated
public class ReportController {
    private final AppValidator appValidator;
    private final IReportService reportService;

    @Autowired
    public ReportController(AppValidator appValidator, IReportService reportService) {
        this.appValidator = appValidator;
        this.reportService = reportService;
    }

    @GetMapping("/weekly")
    @Operation(summary = "Get user's profiles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<?> generateWeeklyReport(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable @Valid @ExistUserById Long userId,
                                           HttpServletResponse httpServletResponse) throws IOException {
        ResponseEntity<?> validationError = appValidator.validateReport(userDetails, userId);
        if(validationError != null) {
            return validationError;
        }

        WeeklyReportData report = reportService.generateWeeklyReport(userId);
        Workbook workbook = AppUtils.generateWeeklyReport(report);

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
        try(OutputStream out = httpServletResponse.getOutputStream()){
            workbook.write(out);
        }
        String data = "File is attached successfully";
        BaseResponse<String, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
