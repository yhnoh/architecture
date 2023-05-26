package com.example.cleanarchitecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.PackageMatcher;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

public class ArchitectureRuleTest {

    @Test
    void layeredArchitectureRule(){
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example.cleanarchitecture.layered_architecture.member");

        ArchRule servicePackageAndNamingRule = ArchRuleDefinition.classes().that()
                .resideInAPackage("com.example.cleanarchitecture.layered_architecture.*.service")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("ServiceImpl");

        servicePackageAndNamingRule.check(importedClasses);

        ArchRuleDefinition.noClasses().that().resideInAPackage("com.example.cleanarchitecture.layered_architecture.*.service")
                .should().dependOnClassesThat().resideInAPackage("")

//        ArchRule archRule = ArchRuleDefinition.classes().that().resideInAPackage("controller")
//                .should().haveSimpleNameEndingWith("Controller")
//                .should().resideInAPackage("..service..").as("서비스 코드 위치");
//
//        archRule.check(importedClasses);

    }
}
