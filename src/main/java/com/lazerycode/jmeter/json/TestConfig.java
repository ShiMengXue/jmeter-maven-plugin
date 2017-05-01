package com.lazerycode.jmeter.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;

import com.jayway.jsonpath.JsonPath;

/**
 * Allows user to specify the files he wants to check.
 *
 */
public class TestConfig {
	private String jsonData;

	public TestConfig(InputStream jsonFile) throws MojoExecutionException {
		try {
			jsonData = IOUtils.toString(jsonFile, Charset.forName("UTF-8"));
		} catch (Exception ex) {
			throw new MojoExecutionException(ex.getMessage(), ex);
		}
	}

	public TestConfig(File jsonFile) throws MojoExecutionException {
		try (FileReader jsonFileReader = new FileReader(jsonFile)) {
			jsonData = IOUtils.toString(jsonFileReader);
		} catch (Exception ex) {
			throw new MojoExecutionException(ex.getMessage(), ex);
		}
	}

	public String getFullConfig() {
		return jsonData;
	}

	public void writeResultFilesConfigTo(String configLocation) throws MojoExecutionException {
		try (FileWriter file = new FileWriter(configLocation)) {
			file.write(jsonData);
		} catch (Exception ex) {
			throw new MojoExecutionException(ex.getMessage(), ex);
		}
	}

	public void setResultsFileLocations(List<String> resultFileLocations) {
		jsonData = JsonPath.parse(jsonData).set("$.resultFilesLocations", resultFileLocations).jsonString();
	}

	public List<String> getResultsFileLocations() {
		return JsonPath.read(jsonData, "$.resultFilesLocations");
	}

	public void setResultsOutputIsCSVFormat(boolean isCSVFormat) {
		jsonData = JsonPath.parse(jsonData).set("$.resultsOutputIsCSVFormat", isCSVFormat).jsonString();
	}

	public boolean getResultsOutputIsCSVFormat() {
		return JsonPath.read(jsonData, "$.resultsOutputIsCSVFormat");
	}

	public void setGenerateReports(boolean generateReports) {
		jsonData = JsonPath.parse(jsonData).set("$.generateReports", generateReports).jsonString();
	}

	public boolean getGenerateReports() {
		return JsonPath.read(jsonData, "$.generateReports");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TestConfig that = (TestConfig) o;

		return jsonData != null ? jsonData.equals(that.jsonData) : that.jsonData == null;
	}

}