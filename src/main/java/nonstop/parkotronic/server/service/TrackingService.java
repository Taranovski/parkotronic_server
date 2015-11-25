/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nonstop.parkotronic.server.service;

import java.util.List;
import nonstop.parkotronic.server.dto.DeviceLocation;
import nonstop.parkotronic.server.dto.SpotInfo;

/**
 *
 * @author Alyx
 */
public interface TrackingService {

    public void saveDeviceLocationInformation(SpotInfo request);

    public List<DeviceLocation> getAllAvailableDeviceLocationInformation();

    public DeviceLocation getDeviceLocationInformationByDeviceAdress(String adress);
    
}
