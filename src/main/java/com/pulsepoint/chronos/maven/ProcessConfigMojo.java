package com.pulsepoint.chronos.maven;


import com.google.gson.JsonObject;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import static com.pulsepoint.chronos.maven.Utils.readJson;
import static com.pulsepoint.chronos.maven.Utils.writeJson;


@Mojo(name = "processConfig", defaultPhase = LifecyclePhase.VERIFY)
public class ProcessConfigMojo extends AbstractChronosMojo
{

    /**
     * Path to JSON file to read from when processing Chronos config.
     */
    @Parameter(property = "sourceChronosConfigFile", defaultValue = "${basedir}/chronos.json")
    private String sourceChronosConfigFile;

    /**
     * (Optional) Container image for this job.
     */
    @Parameter(property = "image")
    private String image;

    /**
     * (Optional) Name of the job.
     */
    @Parameter(property = "name")
    private String name;

    /**
     * (Optional) The scheduling for the job, in ISO 8601 format.
     */
    @Parameter(property = "schedule")
    private String schedule;

    /**
     * (Optional) Amount of Mesos CPUs for this job.
     */
    @Parameter(property = "cpus")
    private String cpus;

    /**
     * (Optional) Amount of Mesos Memory (in MB) for this job.
     */
    @Parameter(property = "mem")
    private String mem;

    /**
     * (Optional) Amount of Mesos Disk (in MB) for this job.
     */
    @Parameter(property = "disk")
    private String disk;

    /**
     * (Optional) Command to execute.
     */
    @Parameter(property = "command")
    private String command;

    public void execute()
        throws MojoExecutionException
    {

        JsonObject jsonObject = readJson(sourceChronosConfigFile);

        getLog().info("Original json: " + jsonObject.toString());

        if (image != null && jsonObject.has("container")) {
            jsonObject.getAsJsonObject("container").addProperty("image", image);
        }

        if (name != null) {
            jsonObject.addProperty("name", name);
        }

        if (schedule!=null) {
            jsonObject.addProperty("schedule", schedule);
        }

        if (cpus!=null) {
            jsonObject.addProperty("cpus", cpus);
        }

        if (mem!=null) {
            jsonObject.addProperty("mem", mem);
        }

        if (disk!=null) {
            jsonObject.addProperty("disk", disk);
        }

        if (command!=null) {
            jsonObject.addProperty("command", command);
        }

        getLog().info("Modified json: " + jsonObject.toString());
        writeJson(jsonObject, finalChronosConfigFile);

    }
}
