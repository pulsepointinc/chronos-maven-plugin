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
     * Default is ${basedir}/chronos.json
     */
    @Parameter(property = "sourceChronosConfigFile", defaultValue = "${basedir}/chronos.json")
    private String sourceChronosConfigFile;

    /**
     * Image name as specified in pom.xml.
     */
    @Parameter(property = "image")
    private String image;

    /**
     * Name to use for the Chronos config.
     */
    @Parameter(property = "name")
    private String name;

    public void execute()
        throws MojoExecutionException
    {

        JsonObject jsonObject = readJson(sourceChronosConfigFile);

        getLog().info("Original json: " + jsonObject.toString());

        if (image != null) {
            jsonObject.getAsJsonObject("container").addProperty("image", image);
        }

        if (name != null) {
            jsonObject.addProperty("name", name);
        }

        getLog().info("Modified json: " + jsonObject.toString());
        writeJson(jsonObject, finalChronosConfigFile);

    }
}
