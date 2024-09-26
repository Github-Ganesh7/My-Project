package com.my.project.core.models.impl;


import com.day.cq.wcm.api.Page;
import com.my.project.core.models.BasicDetails;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations  .injectorspecific.ValueMapValue;

@Model(adaptables = {SlingHttpServletRequest.class},
adapters = BasicDetails.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BasicDetailsImpl implements BasicDetails {

    @ValueMapValue
    String firstname;

    @ValueMapValue
    String lastname;

    @ValueMapValue
    String contactnumber;

    @ScriptVariable
    Page currentPage;

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

    @Override
    public String getPageTitle() {
        return currentPage.getTitle();
    }
}
