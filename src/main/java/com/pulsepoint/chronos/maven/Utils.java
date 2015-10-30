package com.pulsepoint.chronos.maven;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    private Utils() {
    }

    public static final JsonObject readJson(String fileName) throws MojoExecutionException {
        try {
            JsonObject jsonObject;
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
            return jsonObject;
        }catch (IOException e){
            throw new MojoExecutionException("Failed to read Chronos config file", e);
        }
    }

    public static final void writeJson(JsonObject jsonObject, String fileName) throws MojoExecutionException {

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to write Chronos config file", e);
        }
    }
}