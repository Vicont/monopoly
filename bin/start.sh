cd $(dirname $0)
cd ../
mvn package
java -jar ./target/monopoly-1.0.jar