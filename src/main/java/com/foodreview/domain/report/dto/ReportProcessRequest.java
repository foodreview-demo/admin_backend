package com.foodreview.domain.report.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportProcessRequest {

    public enum ProcessAction {
        RESOLVE, REJECT
    }

    @NotNull(message = "처리 액션은 필수입니다")
    private ProcessAction action;

    private String adminNote;

    private boolean deleteReview = false;
}
