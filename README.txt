TASK

 Create a Java based command line utility that will interact with the stack overflow API v2.2 
 (http://api.stackexchange.com/docs). The utility is to accept at least one command line 
 argument called type and in some cases a 2nd argument. Depending on the type passed to the 
 utility, it will query stack overflow and simply print out the results with each new entry on 
 a separate line. Any outside libraries can be used. Appropriate tests should be written for 
 the code. The resulting solution will be judged on overall code design, test quality, code 
 quality, code readability, and adherence to assignment.

 Tags:
 http://api.stackexchange.com/docs/tags
 A type value of tags (type=tags) will result in listing the name of all tags ordered by the 
 name of the tag in ascending order. The first 100 results should be displayed. A 2nd command 
 line argument can be optionally provided which would filter the results to only those tags 
 that contain the text of the 2nd command line argument.

 Badges:
 http://api.stackexchange.com/docs/badges
 A type value of badges (type=badges) will result in an output of the name and award count of 
 each badge (i.e. cryptography - 16), the first 500 results should be returned. A 2nd command 
 line argument must be provided when type=badges. The acceptable values for the 2nd argument 
 are bronze, gold or silver. This value should be used when querying for badges and only those 
 tags that match the bronze, gold or silver criteria should be returned.

 Any unknown type given or no type specified should simply result in an error message. The 
 same if the wrong number of arguments are provided. After outputting the results, the 
 program can simply end. There is no need to add any additional functionality besides that 
 described in the assignment description above. If the stack overflow API is unavailable a 
 simple error message should be given as the output. When querying the API, it should always 
 be for the site “stackoverflow”, i.e. site=stackoverflow for the query string

IDEA

 Create an easy configurable tool that allows to add new connectors without main class rebuilding

BUILD
 
 Requirements: java7, maven3 (I am using 3.0.4)
 Run mvn clean package assembly:single from the root folder. All the tests are run during the 
 building. Built artifacts are AxTool\target\AxTool-1.0.0-jar-with-dependencies.jar and 
 AxStackoverflow\target\AxStackoverflow-1.0.0-jar-with-dependencies.jar. All required 
 dependences are included into corresponded packages.
 You can run mvn eclipse:eclipse to generate project files for eclipse IDE or corresponded goal 
 for another IDE (but it isn't tested).

USAGE

 There are copied by me artifacts in dist folder. To run the tool execute run.bat <parameters>. 
 It is possible to start tool with java -jar AxTool-1.0.0-jar-with-dependencies.jar, but there 
 are not any registered connectors in this case. Connectors to Stackexchange are located in 
 AxStackoverflow-1.0.0-jar-with-dependencies.jar and we have to add the jar to classpath to the 
 tool found its.
 Sometimes tool (or tests) can fail because of stackexchange day and second requests limits.

CONFIGURATION
 
 It is possible to configure the tool using java properties. Default configuration are set to 
 satisfy the task.
 
 Properties for configuration:
 ax.mainConnector - defines main facade class. It must implements IQueryConnector interface. 
                    Default value is com.hazehorizon.ax.SelfInitializedConnectorComposite.

 ax.connectorsPackage - defines package for connectors searching. Default value is 
                    "com.hazehorizon.ax.connector"
 ax.includeClassesExp - defines regular expression for connector class names. Default value 
                    is ".*"
 ax.excludeClassesExp - defines regular expression to exclude classes from available connectors 
                    list. Default value is ".*(ConnectorComposite|SelfInitializedConnectorComposite)$"
 
 Connector class is listed only if it implements IQueryConnector interface; it is neither interface 
 nor abstract class; it name mathes ax.includeClassesExp value and doesn't match 
 ax.excludeClassesExp value.

 Any other parameters with name ax.<field name> can be defined and it value will be set to main 
 facade filed (only if the implementation has such field).

 ax.connectors.<type>.<fieldName> property can be defined to set value to connector implementation 
 for <type> if it has field with name <fieldName>.
 
 Example: 
 -Dax.connectors.tags.defaultLimit=500 changes default limit for tags query to 500 (default is 100).
 -Dax.connectors.tags.key=rwerwrw changes key value required by stackexchange.
 -Dax.connectors.badges.site=anothersite changes site parameter request from stackoverflow value for 
  badges connector.