package com.my.project.core.models.impl;

import com.my.project.core.models.BasicDetails;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


import javax.inject.Inject;

@Model(adaptables = Resource.class,
adapters = BasicDetails.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BasicDetailsImpl implements BasicDetails {

    @Inject
    String firstname;

    @Inject
    String lastname;

    @Inject
    String contactnumber;

    @Override
    public String getFirstName() {
        return firstname;
    }

    @Override
    public String getLastName() {
        return lastname;
    }

    @Override
    public String getContactNumber() {
        return contactnumber;
    }
}
