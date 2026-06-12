package ru.pogosian;

import org.junit.jupiter.api.Test;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.factories.*;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.services.BuildConfigurationService;
import ru.pogosian.business.services.buildConfigurationServiceImpl;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BuildConfigurationServiceTests {
    @Test
    void shouldBuildConfigurationSuccessfullyAndCalculatePrice()
    {
        CarModelRepository usingCarModelRepository = mock(CarModelRepository.class);
        CarDetailsRepository usingCarDetailsRepository = mock(CarDetailsRepository.class);
        CarConfigurationRepository usingCarConfigurationRepository = mock(CarConfigurationRepository.class);

        UUID modelId = UUID.randomUUID();
        CarModel model = CarModel.builder()
                .modelId(modelId)
                .modelBrand("JIGA")
                .modelName("wishnewaya semerka")
                .basePrice(BigDecimal.valueOf(400000000))
                .build();
        when(usingCarModelRepository.findById(modelId)).thenReturn(model);

        DetailFactory wheelFactory = new WheelDetailsFactory();
        DetailFactory interiorFactory = new InterirorFactory();
        DetailFactory transmissionFactory = new TransmissionDetailFactory();
        DetailFactory steeringWheelFactory = new SteeringWheelFactory();

        CarDetails wheel = wheelFactory.create("base wheels", Set.of(modelId), BigDecimal.valueOf(100));
        CarDetails interior = interiorFactory.create("base interiors", Set.of(modelId), BigDecimal.valueOf(200));
        CarDetails steeringWheel = steeringWheelFactory.create("base steeringWheels", Set.of(modelId), BigDecimal.valueOf(300));
        CarDetails transmission = transmissionFactory.create("base transmissions", Set.of(modelId), BigDecimal.valueOf(400));

        BuildConfigurationService configuratior = new buildConfigurationServiceImpl(usingCarModelRepository, usingCarConfigurationRepository, usingCarDetailsRepository);

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
}
