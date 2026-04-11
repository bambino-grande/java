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
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.DetailService;
import ru.pogosian.presentation.DTO.CarFilterDto;
import ru.pogosian.presentation.DTO.request.CreateCarFromModelRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarDetailRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarRequest;
import ru.pogosian.presentation.DTO.response.CarDetailResponse;
import ru.pogosian.presentation.DTO.response.CarResponse;
import ru.pogosian.presentation.mapper.CarDetailMapper;
import ru.pogosian.presentation.mapper.CarFilterMapper;
import ru.pogosian.presentation.mapper.CarMapper;
import ru.pogosian.presentation.mapper.ColorConverter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/details")
@AllArgsConstructor
@Tag(name = "details", description = "операции с комплектующими автомобилей")
public class CarDetailController {
    private final CarDetailMapper carDetailMapper;
    private final DetailService detailService;


    @GetMapping("/{id}")
    @Operation(summary = "получить деталь", description = "возвращает деталь по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "деталь", content = @Content(schema = @Schema(implementation = CarDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarDetailResponse getDetail(@PathVariable UUID id) {
        return carDetailMapper.toDto(detailService.viewCarDetails(id));
    }


    @GetMapping("/find-all")
    @Operation(summary = "получить список комплектующих", description = "возвращает все комплектующие.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список комплектующих", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarDetailResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<CarDetailResponse> findAllDetails(Pageable pageable) {
        return detailService.viewAllCars(pageable).stream().map(carDetailMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "создать деталь", description = "создаёт новую деталь.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "деталь", content = @Content(schema = @Schema(implementation = CarDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarDetailResponse createCarDetail(@RequestBody CreateOrUpdateCarDetailRequest createOrUpdateCarDetailRequest) {
        return carDetailMapper.toDto(detailService.addCarDetails(carDetailMapper.toDomain(createOrUpdateCarDetailRequest)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "обновить деталь", description = "обновляет деталь по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "обновлённая деталь", content = @Content(schema = @Schema(implementation = CarDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarDetailResponse updateCarDetail(@PathVariable UUID id, @RequestBody CreateOrUpdateCarDetailRequest createOrUpdateCarDetailRequest) {
        return carDetailMapper.toDto(detailService.updateCarDetails(carDetailMapper.toDomain(createOrUpdateCarDetailRequest, id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удалить деталь", description = "выполняет мягкое удаление детали.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "деталь удалена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void deleteCarDetail(@PathVariable UUID id) {
        detailService.deleteCar(id);
    }
}


