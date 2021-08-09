package pt.frodrigues.challenge;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("pt.frodrigues.challenge");

        noClasses()
            .that()
            .resideInAnyPackage("pt.frodrigues.challenge.service..")
            .or()
            .resideInAnyPackage("pt.frodrigues.challenge.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..pt.frodrigues.challenge.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
