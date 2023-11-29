package edu.bbte.idde.bfim2114.backend.service;

import lombok.Getter;

@Getter
public class ServiceFactory {

    private static ServiceFactory instance;
    private final UserService userService;
    private final HardwareService hardwareService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        hardwareService = new HardwareServiceImpl();
    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
}