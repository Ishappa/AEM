package com.adobe.aem.guides.wknd.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = UseOne.class)
@Designate(ocd = UserOne_Imple.User.class)
public class UserOne_Imple implements UseOne {

    @ObjectClassDefinition(name = "User Information",description = "Enter here user Information")
    public @interface User{
        @AttributeDefinition(
                name = "User_Name",
                description = "Enter here User Name",
                type = AttributeType.STRING
        )
        public String name() default "user";

        @AttributeDefinition(
                name = "Age",
                description = "Enter the age",
                type = AttributeType.INTEGER
        )
        public int age();

        @AttributeDefinition(
                name = "Location",
                description = "Enter the Location",
                type = AttributeType.STRING
        )
        public String location();

    }
//    -------------------------------Above code is for structure of OSGI Service-------------------------------

    private String name;
    private int age;
    private String location;

    @Activate
    public void activate(User user){
        this.name = user.name();
        this.age = user.age();
        this.location = user.location();
    }

    //---------------Implementing UserOne Interface Methods here-----------------------

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getLocation() {
        return location;
    }

}
