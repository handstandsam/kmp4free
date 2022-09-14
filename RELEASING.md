Releasing
=========

1. Change the version in `kmp4free/gradle.properties` to a non-SNAPSHOT version.
2. Update the `README.md` to reflect the new version number.
3. `git commit -am "Release X.Y.Z."` (where X.Y.Z is the new version)
4. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
5. `git push && git push --tags` to trigger CI to deploy the release.
6. Update the `gradle.properties` to the next SNAPSHOT version.
7. `git commit -am "Prepare next development version."`
8. `git push`
