/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nonstop.parkotronic.server.controller;

import nonstop.parkotronic.server.dto.DeviceLocation;
import java.util.List;
import nonstop.parkotronic.server.service.TrackingService;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import nonstop.parkotronic.server.dto.Dto;
import nonstop.parkotronic.server.dto.SpotInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alyx
 */
@RestController
@RequestMapping("/nonstop")
public class Controller {

    @Inject
    private TrackingService trackingService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Dto respond(@RequestParam(value = "name", defaultValue = "World") String request) {
        String name = "hello, " + request;
        System.out.println(name);
        Dto dto = new Dto();
        dto.string = name;
        return dto;
    }

    @RequestMapping(value = "/spotInfo", method = RequestMethod.POST)
    public Dto respondForSpotInfoCall(@RequestBody SpotInfo request) {
        trackingService.saveDeviceLocationInformation(request);

        String requestString = request.toString();
        System.out.println(requestString);
        Dto dto = new Dto();
        dto.string = requestString;
        return dto;
    }

    @RequestMapping(value = "/deviceLocations", method = RequestMethod.GET)
    public List<DeviceLocation> getAllDeviceLocations() {
        return trackingService.getAllAvailableDeviceLocationInformation();
    }

    @RequestMapping(value = "/deviceLocations/{adress}", method = RequestMethod.GET)
    public DeviceLocation getDeviceLocationByAdress(@PathParam(value = "adress") String adress) {
        return trackingService.getDeviceLocationInformationByDeviceAdress(adress);

    }
}
