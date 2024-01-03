package edu.bbte.idde.bfim2114.springbackend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bfim2114.springbackend.model.Category;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.CategoryService;
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
import java.util.Map;

@RequiredArgsConstructor
@Profile("data-gen")
@Slf4j
@Component
public class DataGen {

    private final UserService userService;
    private final HardwareService hardwareService;
    private final CategoryService categoryService;

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
        Map data = mapper.readValue(jsonFile, Map.class);

        List<Map<String, Object>> categories = (List<Map<String, Object>>) data.get("categories");
        for (Map<String, Object> categoryData : categories) {
            Category category = new Category();
            category.setName((String) categoryData.get("name"));
            category.setDescription((String) categoryData.get("description"));
            categoryService.save(category);
        }

        List<Map<String, Object>> hardwareParts = (List<Map<String, Object>>) data.get("hardwareParts");
        for (Map<String, Object> partData : hardwareParts) {
            Category category = categoryService.findById(((Number) partData.get("categoryId")).longValue());
            HardwarePart hardwarePart = new HardwarePart(
                (String) partData.get("name"),
                (String) partData.get("manufacturer"),
                ((Number) partData.get("price")).doubleValue(),
                (String) partData.get("description"),
                userService.findById(((Number) partData.get("userId")).longValue()),
                category
            );
            log.warn("JSON HardwarePart Category: {}", hardwarePart.getCategory());
            hardwareService.create(hardwarePart);
        }

        log.info("Generated hardware parts from JSON");
    }
}
