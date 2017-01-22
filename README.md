Web Crawler build and install/run instructions

Pre-requites 
1. Install NetBeans 8.2 with JDK 8 which comes with Maven
2. Configure/add Maven to system path if you want to build and run any java/maven projects

Build and run projects
1. Navigate to the path where the project pom.xml file located from the command prompt
2. Type "mvn clean install"
3. cd /target if build successful
4. java -jar WebCrawler-1.0-jar-with-dependencies.jar http://wiprodigital.com
