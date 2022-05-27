plugins {
  id("com.handstandsam.kmp4free")
}

dependencies {
  implementation(libs.kotlin.stdlib)
  testImplementation(libs.kotlin.test.common)
  testImplementation(libs.truth)
}
