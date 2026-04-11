package ru.pogosian.presentation.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.business.services.TestDriveService;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.presentation.DTO.request.CreateComplectationCarOrderRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateTestDriveRequestRequest;
import ru.pogosian.presentation.DTO.request.UpdateComplectationCarOrderRequest;
import ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse;
import ru.pogosian.presentation.DTO.response.TestDriveRequestResponse;
import ru.pogosian.presentation.mapper.ComplectationCarOrderMapper;
import ru.pogosian.presentation.mapper.TestDriveRequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/test-drive")
@AllArgsConstructor
@Tag(name = "test-drive", description = "операции с заявками на тестдрайв")
public class TestDriveController {
    private final TestDriveRequestMapper testDriveRequestMapper;
    private final TestDriveService testDriveService;

    @GetMapping("/find-all")
    @Operation(summary = "получить список заявок", description = "возвращает список заявок на тестдрайв")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список заявок", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TestDriveRequestResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<TestDriveRequestResponse> findAllTestDriveRequests(Pageable pageable) {
        return testDriveService.listTestDriveRequests(pageable).stream().map(testDriveRequestMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "создать заявку", description = "создаёт новую заявку на тест-драйв.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданная заявка", content = @Content(schema = @Schema(implementation = TestDriveRequestResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public TestDriveRequestResponse createTestDriveRequest(@RequestBody CreateOrUpdateTestDriveRequestRequest createOrUpdateTestDriveRequestRequest) {
        return testDriveRequestMapper.toDto(testDriveService.createTestDriveRequest(createOrUpdateTestDriveRequestRequest.clientId(), createOrUpdateTestDriveRequestRequest.carId(),  createOrUpdateTestDriveRequestRequest.testDriveStartAt()));
    }

    @PutMapping("/make-available/{id}")
    @Operation(summary = "сделать автомобиль доступным для тест-драйва")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "автомобиль теперь доступен для тест-драйва"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void makeCarAvailableForTestDrive(@PathVariable UUID id) {
        testDriveService.makeCarAvailableForTestDrive(id);
    }

    @PutMapping("/unmake-available/{id}")
    @Operation(summary = "снять доступность автомобиля для тест-драйва")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "автомобиль больше недоступен для тест-драйва"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void unMakeCarAvailableForTestDrive(@PathVariable UUID id) {
        testDriveService.unmakeCarAvailableForTestDrive(id);
    }
}