package com.quickorderservice.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value= "develop")
@PropertySource({"classpath:profile/develop/application.properties"})
public class ProfileDevelop {
}
