package com.example.AdvancedUser.config;


import com.example.AdvancedUser.model.Zone;
import com.example.AdvancedUser.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public void run(String... args) {
        if (zoneRepository.count() == 0) {
            // Initialize 32 zones with sample data
            // Note: You'll need to update account numbers, bank codes, and amounts as per your requirements
            String[] zoneNames = {
                    "Zone 1", "Zone 2", "Zone 3", "Zone 4", "Zone 5",
                    "Zone 6", "Zone 7", "Zone 8", "Zone 9", "Zone 10",
                    "Zone 11", "Zone 12", "Zone 13", "Zone 14", "Zone 15",
                    "Zone 16", "Zone 17", "Zone 18", "Zone 19", "Zone 20",
                    "Zone 21", "Zone 22", "Zone 23", "Zone 24", "Zone 25",
                    "Zone 26", "Zone 27", "Zone 28", "Zone 29", "Zone 30",
                    "Zone 31", "Zone 32"
            };

            for (String zoneName : zoneNames) {
                Zone zone = new Zone();
                zone.setName(zoneName);
                zone.setAccountNumber("0000000000"); // Update with actual account numbers
                zone.setBankCode("058"); // Update with actual bank codes (058 for GTBank, 011 for First Bank, etc.)
                zone.setBankName("Sample Bank"); // Update with actual bank names
                zone.setAmountPerRegistrant(5000.0); // Update with actual amount per registrant
                zoneRepository.save(zone);
            }
        }
    }
}


