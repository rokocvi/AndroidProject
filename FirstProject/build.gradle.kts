// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

subprojects {
    afterEvaluate {
        // Primijeni Google Services plugin samo na aplikacijske module (com.android.application)
        if (plugins.hasPlugin("com.android.application")) {
            // Apply Google Services plugin za aplikacijske module
            apply(plugin = "com.google.gms.google-services")
        }
    }
}


