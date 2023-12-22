# Advent of Code 2023

---

[Advent of Code Home](https://adventofcode.com/2023)

---

Language: kotlin `1.9`

Target JVM: `17`

Gradle version: `8.5`

---

Gradle command to run code for individual day:

```shell
# generic run command format
./gradlew run -Dday={day} --args='{part#} {test|run}'
# all options for day=one -> test and run for parts 1 and 2
./gradlew run -Dday=one --args='1 test'
./gradlew run -Dday=one --args='1 run'
./gradlew run -Dday=one --args='2 test'
./gradlew run -Dday=one --args='2 run'
# additional samples for other days
./gradlew run -Dday=two --args='2 run'
./gradlew run -Dday=three --args='2 test'
```