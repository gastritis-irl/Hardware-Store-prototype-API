package edu.bbte.idde.bfim2114.springbackend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartOutDTO;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.HardwareService;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Profile("data-gen")
@Slf4j
@Component
public class DataGen {

    private final UserService userService;

    private final HardwareService hardwarePartService;

    @PostConstruct
    public void generateData() throws IOException {

        log.info("Generating data");

        User user = new User("user@user", BCrypt.hashpw("password",
            BCrypt.gensalt()), "USER", new ArrayList<>());
        User admin = new User("admin@admin", BCrypt.hashpw("password",
            BCrypt.gensalt()), "ADMIN", new ArrayList<>());
        userService.create(user);
        userService.create(admin);

        log.info("Generated users");

        log.info("Generating data from JSON");

        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("DataGen.json");
        File jsonFile = resource.getFile();
        List<HardwarePartOutDTO> hardwareParts = mapper.readValue(jsonFile,
            new TypeReference<>() {
            });


        for (HardwarePartOutDTO part : hardwareParts) {
            HardwarePart hardwarePart = new HardwarePart(
                part.getName(), part.getManufacturer(), part.getCategory(),
                part.getPrice(), part.getDescription(), userService.findById(part.getUserId())
            );
            hardwarePartService.create(hardwarePart);
        }

        log.info("Generated hardware parts from JSON");
    }

}
