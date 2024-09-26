package com.my.project.core.models.impl;


import com.day.cq.wcm.api.Page;
import com.my.project.core.models.BasicDetails;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations  .injectorspecific.ValueMapValue;

import javax.inject.Inject;

@Model(adaptables = {SlingHttpServletRequest.class},
adapters = BasicDetails.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BasicDetailsImpl implements BasicDetails {

    @Inject
    @Via("resource")
    String firstname;

    @ValueMapValue
    @Default(values="AEM (Default from model)")
    String lastname;

    @ValueMapValue
    String contactnumber;

    @ScriptVariable
    Page currentPage;

    @RequestAttribute(name="rAttribute")
    private String reqAttribute;

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

    @Override
    public String getRequestAttribute() {
        return reqAttribute;
    }
}
