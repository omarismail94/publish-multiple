package com.example;
import javax.inject.Inject;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.attributes.Usage;
import org.gradle.api.component.AdhocComponentWithVariants;
import org.gradle.api.component.SoftwareComponentFactory;
public class MyPlugin implements Plugin<Project> {
  private final SoftwareComponentFactory softwareComponentFactory;
  // https://docs.gradle.org/current/userguide/publishing_customization.html
  @Inject
  MyPlugin(SoftwareComponentFactory softwareComponentFactory) {
    this.softwareComponentFactory = softwareComponentFactory;
  }
  @Override
  public void apply(Project project) {
    AdhocComponentWithVariants component = softwareComponentFactory.adhoc("myComponent");
    project.getComponents().add(component);
    Configuration foo = project.getConfigurations().create("foo");
    foo.getAttributes().attribute(
        Usage.USAGE_ATTRIBUTE,
        project.getObjects().named(Usage.class, Usage.JAVA_RUNTIME)
    );
    foo.setCanBeConsumed(true);
    foo.setCanBeResolved(false);

    component.addVariantsFromConfiguration(foo, configurationVariantDetails -> {
      configurationVariantDetails.mapToMavenScope("runtime");
    });
  }
}