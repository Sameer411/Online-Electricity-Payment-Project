package com.group2.capstone.EBPaymentSystem;

import com.group2.capstone.EBPaymentSystem.authentication.models.Role;
import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.authentication.repository.RoleRepository;
import com.group2.capstone.EBPaymentSystem.authentication.repository.UserRepository;
import com.group2.capstone.EBPaymentSystem.billing.models.*;
import com.group2.capstone.EBPaymentSystem.billing.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class EBPaymentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBPaymentSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode, MeterReadingsRepo meterReadingsRepo, UserProfileRepo userProfileRepo, MeterRepo meterRepo, PropertyTypeRepo propertyTypeRepo, AddressRepo addressRepo,PropertyRepo propertyRepo) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("DISTRICT_OFFICIAL"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User(1, "admin", passwordEncode.encode("password"), roles);
            userRepository.save(admin);

            Meter meter = meterRepo.save(new Meter(1, 1));
            PropertyType residential = propertyTypeRepo.save(new PropertyType(1, "Residential", 5, 7, 9));
            Address address = addressRepo.save(new Address(1, "100", "Chennai", "Tamilnadu", 600091L, "India"));
            Property property = propertyRepo.save(new Property(1L, meter, residential, address));
            List<Property> properties = new ArrayList<>();
            properties.add(property);

            UserProfile userProfile = new UserProfile(1L, "Aniruth", "9876543210", "aniruth@ar.com", properties);
            userProfileRepo.save(userProfile);
            Set<Role> newRole = new HashSet<>();
            newRole.add(roleRepository.findByAuthority("USER").get());

            User user = new User(2, "aniruth", passwordEncode.encode("password"), newRole, userProfile);
            userRepository.save(user);

            MeterReadings meterReading = new MeterReadings(1, LocalDate.of(2023, 06, 30), 850, meter);
            meterReadingsRepo.save(meterReading);
        };
    }

}