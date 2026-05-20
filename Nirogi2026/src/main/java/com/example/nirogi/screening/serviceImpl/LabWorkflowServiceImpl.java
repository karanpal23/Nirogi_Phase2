package com.example.nirogi.screening.serviceImpl;


import com.example.nirogi.screening.dto.*;
import com.example.nirogi.screening.entity.*;
import com.example.nirogi.screening.enums.*;
import com.example.nirogi.screening.repository.*;
import com.example.nirogi.screening.service.LabWorkflowService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LabWorkflowServiceImpl
        implements LabWorkflowService {

    private final ScreeningRecordRepository screeningRepo;

    private final ScreeningLabOrderRepository orderRepo;

    private final ScreeningLabTestRepository testRepo;

    private final LabWorkflowRepository workflowRepo;


    @Override
    public LabOrderResponseDTO createLabOrder(
            CreateLabOrderDTO request,
            Long userId
    ) {

        ScreeningRecord screening =
                screeningRepo.findByReferenceId(
                        request.getReferenceId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Invalid reference ID"
                        )
                );

        ScreeningLabOrder existingOrder =
                orderRepo.findByReferenceId(
                        request.getReferenceId()
                ).orElse(null);

        if (existingOrder != null) {

            throw new RuntimeException(
                    "Lab order already exists"
            );
        }

        ScreeningLabOrder order =
                ScreeningLabOrder.builder()

                        .screeningRecordId(
                                screening.getId()
                        )

                        .referenceId(
                                screening.getReferenceId()
                        )

                        .status(
                                LabOrderStatus.ORDERED
                        )

                        .orderedBy(
                                userId
                        )

                        .orderedAt(
                                LocalDateTime.now()
                        )

                        .build();

        orderRepo.save(order);

        for (String test : request.getTests()) {

            ScreeningLabTest labTest =
                    ScreeningLabTest.builder()

                            .labOrderId(
                                    order.getId()
                            )

                            .testCode(
                                    test
                            )

                            .testName(
                                    test
                            )

                            .status(
                                    LabTestStatus.PENDING
                            )

                            .build();

            testRepo.save(labTest);
        }

        LabWorkflow workflow =
                workflowRepo
                .findByScreeningRecordId(
                        screening.getId()
                )
                .orElse(null);

        if (workflow == null) {

            workflow =
                    LabWorkflow.builder()

                            .screeningRecordId(
                                    screening.getId()
                            )

                            .labStatus(
                                    LabStatus.PENDING
                            )

                            .sentAt(
                                    LocalDateTime.now()
                            )

                            .dueDate(
                                    LocalDateTime.now()
                                            .plusDays(7)
                            )

                            .enteredBy(
                                    userId
                            )

                            .build();

        } else {

            workflow.setLabStatus(
                    LabStatus.PENDING
            );

            workflow.setSentAt(
                    LocalDateTime.now()
            );

            workflow.setDueDate(
                    LocalDateTime.now()
                            .plusDays(7)
            );
        }

        workflowRepo.save(workflow);

        screening.setStatus(
                ScreeningStatus.PENDING_LAB
        );

        screeningRepo.save(screening);

        return LabOrderResponseDTO
                .builder()
                .message(
                        "Lab order created successfully"
                )
                .build();
    }


    @Override
	public LabOrderResponseDTO submitLabResults(
	        SubmitLabResultsDTO request,
	        Long userId
	) {
	
	    ScreeningLabOrder order =
	            orderRepo.findByReferenceId(
	                    request.getReferenceId()
	            ).orElseThrow(() ->
	                    new RuntimeException(
	                            "Lab order not found"
	                    )
	            );
	
	    for (LabResultItemDTO item
	            : request.getResults()) {
	
	        ScreeningLabTest test =
	                testRepo.findByLabOrderIdAndTestCode(
	                        order.getId(),
	                        item.getTestCode()
	                ).orElseThrow(() ->
	                        new RuntimeException(
	                                "Lab test not found: "
	                                        + item.getTestCode()
	                        )
	                );
	
	        test.setResultValue(
	                item.getResultValue()
	        );
	
	        test.setRemarks(
	                item.getRemarks()
	        );
	
	        test.setNormalRange(
	                item.getNormalRange()
	        );
	
	        test.setStatus(
	                LabTestStatus.COMPLETED
	        );
	
	        test.setCompletedBy(
	                userId
	        );
	
	        test.setCompletedAt(
	                LocalDateTime.now()
	        );
	
	        testRepo.save(test);
	    }
	
	    List<ScreeningLabTest> tests =
	            testRepo.findByLabOrderId(
	                    order.getId()
	            );
	
	    boolean allCompleted =
	            tests.stream()
	                    .allMatch(t ->
	                            t.getStatus()
	                                    == LabTestStatus.COMPLETED
	                    );
	
	    if (allCompleted) {
	
	        order.setStatus(
	                LabOrderStatus.COMPLETED
	        );
	
	        order.setCompletedAt(
	                LocalDateTime.now()
	        );
	
	    } else {
	
	        order.setStatus(
	                LabOrderStatus.PARTIAL_COMPLETED
	        );
	    }
	
	    orderRepo.save(order);
	
	    if (allCompleted) {
	
	        ScreeningRecord screening =
	                screeningRepo.findByReferenceId(
	                        request.getReferenceId()
	                ).orElseThrow(() ->
	                        new RuntimeException(
	                                "Screening not found"
	                        )
	                );
	
	        screening.setStatus(
	                ScreeningStatus.PENDING
	        );
	
	        screeningRepo.save(screening);
	
	        LabWorkflow workflow =
	                workflowRepo
	                .findByScreeningRecordId(
	                        screening.getId()
	                ).orElseThrow(() ->
	                        new RuntimeException(
	                                "Workflow not found"
	                        )
	                );
	
	        workflow.setLabStatus(
	                LabStatus.COMPLETED
	        );
	
	        workflow.setReceivedAt(
	                LocalDateTime.now()
	        );
	
	        workflowRepo.save(workflow);
	    }
	
	    return LabOrderResponseDTO
	            .builder()
	            .message(
	                    "Lab results submitted successfully"
	            )
	            .build();
	}
    
    
    @Override
    public LabOrderViewDTO getLabTests(
            String referenceId
    ) {

        ScreeningLabOrder order =
                orderRepo.findByReferenceId(
                        referenceId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Lab order not found"
                        )
                );

        List<ScreeningLabTest> tests =
                testRepo.findByLabOrderId(
                        order.getId()
                );

        List<LabTestViewDTO> testViews =
                tests.stream()

                        .map(test ->

                                LabTestViewDTO
                                        .builder()

                                        .testCode(
                                                test.getTestCode()
                                        )

                                        .testName(
                                                test.getTestName()
                                        )

                                        .status(
                                                test.getStatus()
                                                        .name()
                                        )

                                        .resultValue(
                                                test.getResultValue()
                                        )

                                        .remarks(
                                                test.getRemarks()
                                        )

                                        .normalRange(
                                                test.getNormalRange()
                                        )

                                        .build()
                        )

                        .collect(Collectors.toList());

        return LabOrderViewDTO
                .builder()

                .referenceId(
                        referenceId
                )

                .status(
                        order.getStatus()
                                .name()
                )

                .tests(
                        testViews
                )

                .build();
    }
}
