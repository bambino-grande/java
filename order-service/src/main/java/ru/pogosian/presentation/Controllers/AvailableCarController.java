package ru.pogosian.presentation.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pogosian.infrastructure.client.StorageCarClient;
import ru.pogosian.presentation.DTO.response.AvailableCarResponse;
import ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse;
import ru.pogosian.presentation.mapper.AvailableCarMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
@Tag(name = "available-cars", description = "получение автомобилей в наличии через gRPC")
public class AvailableCarController {
    private final StorageCarClient storageCarClient;
    private final AvailableCarMapper availableCarMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @Operation(summary = "получить список машин в наличие")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список машин в наличии", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AvailableCarResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции"),
            @ApiResponse(responseCode = "503", description = "StorageService недоступен"),
            @ApiResponse(responseCode = "404", description = "Машина в наличии не найдена")
    })
    public List<AvailableCarResponse> getAvailableCars(){
        return storageCarClient.getAvailableCars().stream()
                .map(availableCarMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @Operation(summary = "получить машину в наличие")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "машина в наличии", content = @Content(schema = @Schema(implementation = AvailableCarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции"),
            @ApiResponse(responseCode = "503", description = "StorageService недоступен"),
            @ApiResponse(responseCode = "404", description = "Машина в наличии не найдена")
    })
    public AvailableCarResponse getAvailableCar(@PathVariable UUID id){
        return availableCarMapper.toDto(storageCarClient.getAvailableCar(id));
    }
}
