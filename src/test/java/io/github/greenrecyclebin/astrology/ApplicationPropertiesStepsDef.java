package io.github.greenrecyclebin.astrology;import io.cucumber.java8.En;public class ApplicationPropertiesStepsDef implements En {    public ApplicationPropertiesStepsDef() {        Given("application.properties path is ([\\w.]+)", (String path) -> {            // Set application.path here.        });        Then("name is ([\\w.]*)", (String name) -> {            // Assert name here.        });    }}