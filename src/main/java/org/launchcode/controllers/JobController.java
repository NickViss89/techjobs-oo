package org.launchcode.controllers;

import javafx.geometry.Pos;
import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.IdentityHashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
    Job job = jobData.findById(id);
    model.addAttribute("job", job);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes attributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }
        String newName = jobForm.getName();
        Employer newEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location newLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType newPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency newCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());


        Job newJob = new Job(newName, newEmployer, newLocation, newPositionType, newCoreCompetency);

        jobData.add(newJob);

        attributes.addAttribute("id", newJob.getId());


        return "redirect:/job";

    }
}
