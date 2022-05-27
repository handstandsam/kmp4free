plugins {
  kotlin("jvm")
}

dependencies {
  implementation(libs.kotlin.stdlib)
  testImplementation(libs.kotlin.test.common)
  testImplementation(libs.truth)
}
