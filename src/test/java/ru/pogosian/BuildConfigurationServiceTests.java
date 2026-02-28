package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.factories.*;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.services.buildConfigurationService;
import ru.pogosian.business.services.buildConfigurationServiceImpl;
import ru.pogosian.infrastructure.repository.CarConfigurationRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarDetailsRepositoryImpl;
import ru.pogosian.infrastructure.repository.CarModelRepositoryImpl;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildConfigurationServiceTests {
    @Test
    void shouldBuildConfigurationSuccessfullyAndCalculatePrice()
    {
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarConfigurationRepository usingCarConfigurationRepository = new CarConfigurationRepositoryImpl();

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("JIGA")
                .modelName("wishnewaya semerka")
                .basePrice(BigDecimal.valueOf(400000000))
                .build();
        usingCarModelRepository.save(model);

        DetailFactory wheelFactory = new WheelDetailsFactory();
        DetailFactory interiorFactory = new InterirorFactory();
        DetailFactory transmissionFactory = new TransmissionDetailFactory();
        DetailFactory steeringWheelFactory = new SteeringWheelFactory();

        CarDetails wheel = wheelFactory.create("base wheels", Set.of(modelId), BigDecimal.valueOf(100));
        CarDetails interior = interiorFactory.create("base interiors", Set.of(modelId), BigDecimal.valueOf(200));
        CarDetails steeringWheel = steeringWheelFactory.create("base steeringWheels", Set.of(modelId), BigDecimal.valueOf(300));
        CarDetails transmission = transmissionFactory.create("base transmissions", Set.of(modelId), BigDecimal.valueOf(400));

        usingCarDetailsRepository.save(wheel);
        usingCarDetailsRepository.save(interior);
        usingCarDetailsRepository.save(steeringWheel);
        usingCarDetailsRepository.save(transmission);

        buildConfigurationService configuratior = new buildConfigurationServiceImpl(usingCarModelRepository, usingCarConfigurationRepository);

        Set<CarDetails> partsForConfiguration = new HashSet<>();
        partsForConfiguration.add(wheel);
        partsForConfiguration.add(interior);
        partsForConfiguration.add(steeringWheel);
        partsForConfiguration.add(transmission);

        CarConfiguration config = configuratior.buildCarConfiguration(modelId, partsForConfiguration);

        assertNotNull(config.getConfigurationId());
        assertEquals(modelId, config.getConfigurationModelId());
        assertEquals(BigDecimal.valueOf(400000000+ 100 + 200 + 300 + 400), config.getTotalPrice());
    }

    @Test
    void shouldThrowDomainValidationExceptionWhenMissingRequiredDetails()
    {
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarConfigurationRepository usingCarConfigurationRepository = new CarConfigurationRepositoryImpl();

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("JIGA")
                .modelName("wishnewaya semerka")
                .basePrice(BigDecimal.valueOf(400000000))
                .build();
        usingCarModelRepository.save(model);

        DetailFactory wheelFactory = new WheelDetailsFactory();
        CarDetails wheel = wheelFactory.create("base wheels", Set.of(modelId), BigDecimal.valueOf(100));

        buildConfigurationService configuratior = new buildConfigurationServiceImpl(usingCarModelRepository, usingCarConfigurationRepository);

        Set<CarDetails> partsForConfiguration = new HashSet<>();
        partsForConfiguration.add(wheel);

        assertThrows(DomainValidationException.class, () -> configuratior.buildCarConfiguration(modelId, partsForConfiguration));
    }

    @Test
    void shouldThrowIncompatibleComponentExceptionWhenDetailsAreIncompatible()
    {
        CarModelRepository usingCarModelRepository = new CarModelRepositoryImpl();
        CarDetailsRepository usingCarDetailsRepository = new CarDetailsRepositoryImpl();
        CarConfigurationRepository usingCarConfigurationRepository = new CarConfigurationRepositoryImpl();

        UUID modelId1 = UUID.randomUUID();
        UUID modelId2 = UUID.randomUUID();

        CarModel model1 = CarModel.builder()
                .modelId(modelId1)
                .modelBrand("JIGA")
                .modelName("wishnewaya semerka")
                .basePrice(BigDecimal.valueOf(400000000))
                .build();
        usingCarModelRepository.save(model1);


        CarModel model2 = CarModel.builder()
                .modelId(modelId2)
                .modelBrand("JIGA")
                .modelName("wishnewaya semerka")
                .basePrice(BigDecimal.valueOf(400000000))
                .build();
        usingCarModelRepository.save(model2);

        DetailFactory wheelFactory = new WheelDetailsFactory();
        DetailFactory interiorFactory = new InterirorFactory();
        DetailFactory transmissionFactory = new TransmissionDetailFactory();
        DetailFactory steeringWheelFactory = new SteeringWheelFactory();

        CarDetails wheel = wheelFactory.create("base wheels", Set.of(modelId1), BigDecimal.valueOf(100));
        CarDetails interior = interiorFactory.create("base interiors", Set.of(modelId1), BigDecimal.valueOf(200));
        CarDetails steeringWheel = steeringWheelFactory.create("base steeringWheels", Set.of(modelId1), BigDecimal.valueOf(300));
        CarDetails transmission = transmissionFactory.create("base transmissions", Set.of(modelId1), BigDecimal.valueOf(400));

        usingCarDetailsRepository.save(wheel);
        usingCarDetailsRepository.save(interior);
        usingCarDetailsRepository.save(steeringWheel);
        usingCarDetailsRepository.save(transmission);

        buildConfigurationService configuratior = new buildConfigurationServiceImpl(usingCarModelRepository, usingCarConfigurationRepository);

        Set<CarDetails> partsForConfiguration = new HashSet<>();
        partsForConfiguration.add(wheel);
        partsForConfiguration.add(interior);
        partsForConfiguration.add(steeringWheel);
        partsForConfiguration.add(transmission);

        assertThrows(IncompatibleComponentException.class, () -> configuratior.buildCarConfiguration(modelId2, partsForConfiguration));
    }

}
