GPG_TTY=$(tty)
export GPG_TTY
mvn clean deploy -P release -DskipTests
