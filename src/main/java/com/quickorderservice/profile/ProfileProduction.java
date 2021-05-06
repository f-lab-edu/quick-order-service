package com.quickorderservice.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value= "production")
@PropertySource({"classpath:profile/production/application.properties"})
public class ProfileProduction {
}
