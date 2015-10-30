package com.pulsepoint.chronos.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

abstract class AbstractChronosMojo extends AbstractMojo
{
    /**
     * Path to JSON file to write when processing Chronos config
     * Default is ${project.build.directory}/chronos.json
     */
    @Parameter(property = "finalChronosConfigFile", defaultValue = "${project.build.directory}/chronos.json")
    protected String finalChronosConfigFile;
}