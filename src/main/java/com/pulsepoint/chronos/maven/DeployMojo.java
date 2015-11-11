package com.pulsepoint.chronos.maven;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.pulsepoint.chronos.maven.Utils.readJson;

@Mojo(name = "deploy", defaultPhase = LifecyclePhase.DEPLOY)
public class DeployMojo extends AbstractChronosMojo
{

    /**
     * Full URL where to submit chonos put request
     */
    @Parameter(property = "chronosPutURL", defaultValue = "http://localhost:4400/scheduler/iso8601")
    protected String chronosPutURL;

    public void execute()
        throws MojoExecutionException
    {

        JsonObject jsonObject = readJson(finalChronosConfigFile);
        getLog().info("Deploying: " + jsonObject.toString());

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPut put = new HttpPut(chronosPutURL);
        put.addHeader("Content-Type", "application/json");

        StringEntity jsonStringEntity;
        try {
            jsonStringEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            throw new MojoExecutionException("Problem encoding json", e);
        }
        put.setEntity(jsonStringEntity);

        HttpResponse response;
        try {
            response = httpClient.execute(put);
        } catch (IOException e) {
            throw new MojoExecutionException("Problem submitting to chronos", e);
        }

        if (response.getStatusLine().getStatusCode() != 204) {
            throw new MojoExecutionException("Deploy fail with HTTP error: "
                    + response.getStatusLine().getStatusCode());
        }
    }
}
