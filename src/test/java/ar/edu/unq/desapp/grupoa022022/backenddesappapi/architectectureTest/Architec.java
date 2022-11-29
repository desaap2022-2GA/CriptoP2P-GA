package ar.edu.unq.desapp.grupoa022022.backenddesappapi.architectectureTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class Architecture {
    private JavaClasses baseClasses;
    @BeforeEach
    public void setup() {
        baseClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("ar.edu.unq.desapp.grupoa022022.backenddesappapi");
    }

    @Test
    void dtoClassesShouldEndWithDTO(){
        classes().that().resideInAPackage("..dto..")
                .should().haveSimpleNameEndingWith("DTO").check(baseClasses);
    }

   @Test
    void exceptionsClassesShouldEndWithException(){
        classes().that().resideInAPackage("..exceptions..")
                .should().haveSimpleNameEndingWith("Exception").check(baseClasses);
    }

    @Test
    void persistanceClassesShouldEndWithRepo(){
        classes().that().resideInAPackage("..persistence..")
                .should().haveSimpleNameEndingWith("Repo").check(baseClasses);
    }

    @Test
    void serviceIntegrationClassesShouldEndWithService(){
        classes().that().resideInAPackage("..integration..")
                .should().haveSimpleNameEndingWith("Service").check(baseClasses);
    }

    @Test
    void serviceInterfaceserviceClassesShouldEndWithService(){
        classes().that().resideInAPackage("..interfaceservice..")
                .should().haveSimpleNameEndingWith("Service").check(baseClasses);
    }

/*    @Test
    public void serviceImplClassesShouldEndWithService(){
        classes().that().resideInAPackage("..serviceimpl..")
               .should().haveSimpleNameEndingWith("Service").check(baseClasses);
    }
 */
    @Test
    void webserviceClassesShouldEndWithController(){
        classes().that().resideInAPackage("..webservice..")
                .should().haveSimpleNameEndingWith("Controller").check(baseClasses);
    }

    @Test
    void layeredArchitectureShouldBeRespected(){
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..webservice..")
                .layer("Service").definedBy("..service..")
                .layer("Persistence").definedBy("..persistence..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");
    }

    @Test
    void serviceIntegrationClassesShouldHaveSpringServiceAnnotation() {
        classes().that().resideInAPackage("..integration..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service")
                .check(baseClasses);
    }

/*    @Test
    public void serviceImplClassesShouldHaveSpringServiceAnnotation() {
        classes().that().resideInAPackage("..serviceImpl..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service")
                .check(baseClasses);
    }
 */

    @Test
    void controllerClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..webservice..")
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .check(baseClasses);
    }

    @Test
    void authClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..auth..")
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .check(baseClasses);
    }
}