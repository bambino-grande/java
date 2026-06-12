package ru.pogosian.presentation.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.business.services.CarService;
import ru.pogosian.presentation.DTO.CarFilterDto;
import ru.pogosian.presentation.DTO.request.CreateCarFromModelRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarRequest;
import ru.pogosian.presentation.DTO.response.CarResponse;
import ru.pogosian.presentation.mapper.CarFilterMapper;
import ru.pogosian.presentation.mapper.CarMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
@Tag(name = "cars", description = "операции с автомобилями")
public class CarController {
    private final CarMapper carMapper;
    private final CarService carService;
    private final CarFilterMapper carFilterMapper;


    @GetMapping("/{id}")
    @Operation(summary = "получить автомобиль", description = "возвращает автомобиль по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "автомобиль", content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarResponse getCar(@PathVariable UUID id) {
        return carMapper.toDto(carService.viewCar(id));
    }


    @PostMapping("/search")
    @Operation(summary = "получить список автомобилей", description = "возвращает автомобили в наличии с фильтрацией по параметрам поиска.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список автомобилей", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<CarResponse> searchCars(@RequestBody CarFilterDto filterRequest) {
        Filter.CarFilter filter = carFilterMapper.toDomain(filterRequest);
        return carService.filteredCars(filter).stream().map(carMapper::toDto).collect(Collectors.toList());
    }


    @PostMapping("/create-from-model")
    @Operation(summary = "создать автомобиль из модели", description = "создаёт автомобиль с базовой конфигурацией на основе модели.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный автомобиль", content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarResponse createFromModel(@RequestBody CreateCarFromModelRequest createCarFromModelRequest) {
        return carMapper.toDto(carService.CreateCarFromModel(createCarFromModelRequest.carModelId(), createCarFromModelRequest.carName(), createCarFromModelRequest.color(), createCarFromModelRequest.availableForSale(), createCarFromModelRequest.availableForTestDrive()));
    }


    @PostMapping
    @Operation(summary = "создать автомобиль", description = "создаёт автомобиль для указанной комплектации.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный автомобиль", content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarResponse createCar(@RequestBody CreateOrUpdateCarRequest createOrUpdateCarRequest) {
        return carMapper.toDto(carService.createCar(createOrUpdateCarRequest.configurationId(), createOrUpdateCarRequest.carName(), createOrUpdateCarRequest.color(), createOrUpdateCarRequest.availableForSale(), createOrUpdateCarRequest.availableForTestDrive()));
    }


    @PutMapping("/{id}")
    @Operation(summary = "обновить автомобиль", description = "обновляет данные автомобиля.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "обновлённый автомобиль", content = @Content(schema = @Schema(implementation = CarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public CarResponse updateCar(@PathVariable UUID id,@RequestBody CreateOrUpdateCarRequest createOrUpdateCarRequest) {
        return carMapper.toDto(carService.updateCar(id, createOrUpdateCarRequest.configurationId(), createOrUpdateCarRequest.carName(), createOrUpdateCarRequest.color(), createOrUpdateCarRequest.availableForSale(), createOrUpdateCarRequest.availableForTestDrive()));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "удалить автомобиль", description = "выполняет мягкое удаление автомобиля.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "автомобиль удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
    }
}
