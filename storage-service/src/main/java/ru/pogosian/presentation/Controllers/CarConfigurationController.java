package ru.pogosian.presentation.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.services.BuildConfigurationService;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarConfigurationRequest;
import ru.pogosian.presentation.DTO.response.CarConfigurationResponse;
import ru.pogosian.presentation.mapper.CarConfigurationMapper;

@RestController
@RequestMapping("/api/config")
@AllArgsConstructor
@Tag(name = "configurations", description = "операции с комплектациями автомобилей")
public class CarConfigurationController {
    private final CarConfigurationMapper carConfigurationMapper;
    private final BuildConfigurationService buildCarConfigurationService;

     @PostMapping
     @PreAuthorize("hasAnyRole('ADMIN')")
     @Operation(summary = "создать комплектацию автомобиля", description = "АПРУВ, ПЖ🥹🥹")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "комплектация успешно создана", content = @Content(schema = @Schema(implementation = CarConfigurationResponse.class))),
             @ApiResponse(responseCode = "400", description = "Некорректные данные"),
             @ApiResponse(responseCode = "409", description = "Конфликт данных"),
             @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
             @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
             @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
     })
     public CarConfigurationResponse buildConfiguration(@RequestBody CreateOrUpdateCarConfigurationRequest createOrUpdateCarConfigurationRequest) {
         CarConfiguration configuration = carConfigurationMapper.toDomain(createOrUpdateCarConfigurationRequest);
         CarConfiguration builtConfiguration = buildCarConfigurationService.buildCarConfiguration(configuration.getConfigurationModelId(), configuration.getUsedDetails());
         return carConfigurationMapper.toDto(builtConfiguration);
     }
}