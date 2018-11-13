##SpringBoot开发文档
###介绍
1. `Spring Scripts`使用第三方语言。
2. SpringBoot 2.0.5需要Java8或9和spring5.0.8以上,Maven3.2+,Server 3.1+。
3. SpringBoot CLI(Command Line Interface)快速开发工具。
4. SpringBoot打jar包需要加入`spring-boot-maven-plugin`插件
	<pre><code>
	&lt;build>
	  &lt;finalName>最终打包的名称&lt;/finalName>
	  &lt;plugins>
	    &lt;plugin>
	      &lt;groupId>org.springframework.boot&lt;/groupId>
	      &lt;artifactId>spring-boot-maven-plugin&lt;/artifactId>
	      &lt;version>2.0.5.BUILD-SNAPSHOT&lt;/version>
	      &lt;configuration>
	        &lt;mainClass>${start-class}&lt;/mainClass> 主入口类,项目中其他类含有main方法时,不配置可能会报错
	        &lt;layout>ZIP&lt;/layout> 可以是jar、war、zip三种类型
	      &lt;/configuration>
	      &lt;executions>
	        &lt;execution>
	          &lt;goals>
	            &lt;goal>repackage&lt;/goal>
	          &lt;/goals>
	        &lt;/execution>
	      &lt;/executions>
	    &lt;/plugin>
	  &lt;/plugins>
	&lt;/build>
	</code></pre>
<br>
5. *.java不能放到main下(default),必须放到一个包里面,否则会扫描不到。
6. Application.java放到其他类的上层。
7. main()启动方法的标识@SpringBootApplication可以用@EnableAutoConfiguration和@ComponentScan替代。
8. SpringBoot配置Bean的方式,推荐第一种
	+ Java代码实现:@Configuration + @Bean
	+ 导入xml文件实现:@Configuration + @ImportResource("classpath:spring-mybatis.xml")
9. 支持远程debug。
10. maven启动SpringBoot项目:`mvn spring-boot:run`
11. `spring-boot-devtools`加入此依赖开发时会热部署,打包后自动屏蔽此依赖。`spring.devtools.restart.log-condition-evaluation-delta=false`设置了这个属性后会后热部署日志。`spring.devtools.restart.exclude=static/**,public/**`设置这些目录下文件改变不重启。
12. 改变启动的banner
	+ 配置文件banner.txt
	+ SpringApplication.setBanner(...)程序化生成banner。
###SpringBoot事件监听器:
	+ ApplicationStartingEvent：SpringBoot启动开始的时候执行的事件,在该事件中可以获取到SpringApplication对象,可以做一些执行前的设置。取消banner等。
	+ ApplicationEnvironmentPreparedEvent：spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。在该监听中获取到ConfigurableEnvironment后可以对配置信息做操作，例如：修改默认的配置信息，增加额外的配置信息等等。
	+ ApplicationPreparedEvent:spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的。在获取完上下文后，可以将上下文传递出去做一些额外的操作。值得注意的是：在该监听器中是无法获取自定义bean并进行操作的。
	+ ApplicationStartedEvent:ApplicationStartedEvent是在上下文刷新之后，但在调用任何应用程序和命令行运行程序之前发送的。
	+ ApplicationReadyEvent:springboot 加载完成时候执行的事件。
	+ ApplicationFailedEvent:spring boot启动异常时执行事件，在异常发生时，最好是添加虚拟机对应的钩子进行资源的回收与释放，能友善的处理异常信息。
	+ 使用:
		+ 第一步:定义类实现ApplicationListener&lt;T>
		+ 第二步在启动程序中添加监听器。
		+ <pre><code>
		  //第一步
		  public class MyApplicationStartingEvent implements ApplicationListener<ApplicationStartingEvent>{
				@Override
				public void onApplicationEvent(ApplicationStartingEvent event) {
					SpringApplication application = event.getSpringApplication();
					application.setBannerMode(Banner.Mode.OFF);
					System.out.println("------------执行MyApplicationSatringEventListener-----------");
				}
			}
			//第二步
		@SpringBootApplication
		public class SpringbootApplication {
				public static void main(String[] args) {
					SpringApplication application = new SpringApplication(SpringbootApplication.class);
					application.addListeners(new MyApplicationStartingEvent(), new MyApplicationEnvironmentPreparedEvent(),
							new MyApplicationPreparedEvent());
					
					application.run(args);
				}
		}
		</code></pre>
###配置文件
14. @Value("${name}")注入值。生成配置随机值
	<pre><code>
		my.secret=${random.value}
		my.number=${random.int}
		my.bignumber=${random.long}
		my.uuid=${random.uuid}
		my.number.less.than.ten=${random.int(10)}
		my.number.in.range=${random.int[1024,65536]}
	</code></pre>
15. 配置文件的加载,加载顺序如下:
	+ `file:./custom-config/`
	+ `classpath:custom-config/`
	+ `file:./config/`
	+ `file:./`
	+ `classpath:/config/`
	+ `classpath:/`
	+ 使用
		+ 在config下加入user.properties
			<pre><code>
				com.itdage.user.name = scy
				com.itdage.user.age = 24
			</code></pre>
		+ 在User.java中
			<pre><code>
				@Component
				@ConfigurationProperties(prefix = "com.itdage.user")
				@PropertySource("classpath:config/user.properties")
				public class User {
					
					private String name;
					
					private int age;
				
					public String getName() {
						return name;
					}
				
					public void setName(String name) {
						this.name = name;
					}
				
					public int getAge() {
						return age;
					}
				
					public void setAge(int age) {
						this.age = age;
					}
				}

			</code></pre>

###Logging
1. 日志模板包括下列内容
	+ 日期和时间
	+ 日志级别: ERROR,WARN,INFO,DEBUG,TRACE
	+ 进程ID
	+ 分隔符----
	+ 线程名
	+ 日志名(通常是源文件,class文件)
	+ 日志信息
2. 日志输出到文件:
	+ application.properties中加入logging.file或者logging.path
	+ 日志系统是在应用程序生命周期的早期初始化的。因此，在通过@PropertySource注释加载的属性文件中找不到日志属性。
###内嵌服务器
1. `spring-boot-starter-web`内嵌Tomcat  还可用`spring-boot-starter-webflux`
###JSON序列化和反序列化
1. `@JsonComponent``JsonObjectSerializer``@JsonObjectDeserializer`
2. MessageCodesResolver
###静态资源加载
1. 默认加载路径
	+ `/static`
	+ `/public`
	+ `/resources`
	+ `/META-INF/resources`
2. 其他目录的static文件配置
	+ `application.properties`中添加自己的目录即可
		+ `spring.mvc.static-path-pattern=/static/**`
		+ `spring.resources.static-locations=classpath:/static/`
	+ 重写WebMvcConfigureAdapter的配置
		+ <pre><code>
			@Configuration
			public class MyWebAppConfig extends WebMvcConfigurerAdapter {
			    @Override
			    public void addResourceHandlers(ResourceHandlerRegistry registry) {
			        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
			        super.addResourceHandlers(registry);
			    }
			}
		</code></pre>
3. 默认找static下的index.html文件当做主页。
4. 使用模板的时候(如Thymeleaf)寻找文件的位置为:`src/main/resources/templates`
###ErrorController(异常处理待深入了解)
1. SpringBoot默认提供一个`/error`的映射,浏览器访问发生错误时返回`text/html`格式,PostMan访问发生错误时返回`json`格式。
	+ 自定义错误返回格式:继承`BasicErrorController`
2. 定制错误页面
	+ 不使用模板引擎时在src/main/resources/public/eroor/404.html(精确的状态码)
	+ 使用模板引擎时在src/main/resources/templates/error/500.html
###Spring HATEOAS
###CORS支持
1. Spring MVC 4.2版本以后。
2. 
		@Configuration
		public class MyConfiguration {
			@Bean
			public WebMvcConfigurer corsConfigurer() {
				return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**");
				}
			};
		}
###Spirng WebFlux Framework
###Servlets,Filter,Listeners
1. 自动注册带有如下标识的类`@WebServlet``@WebFilter``@WebListener`通过`@ServletComponentScan`
###Security
###JdbcTemplate
1. `@Autowired`自动注入即可
2. `spring-boot-starter-data-jpa`[https://blog.csdn.net/tony308001970/article/details/74999680](https://blog.csdn.net/tony308001970/article/details/74999680 "csdn比较全面的介绍")
###Working with NoSQL Technologies
1. NoSQL技术
	+ MongoDB `spring-boot-starter-data-mongodb`
		+ 使用时自动注入`org.springframework.data.mongodb.MongoDbFactory``MongoTemplate`,默认连接地址`mongodb://localhost/test`
		+ `spring.data.mongodb.uri=mongodb://user:secret@mongo1.example.com:12345,mongo2.example.com:23456/test配置可以更改连接属性地址``spring.data.mongodb.host=mongoserver改主机``spring.data.mongodb.port=27017改端口`
		+ 修改主机和端口只支持MongoDB 2.x  MongoDB 3.x需要在uri中一起制定配置
	+ Elasticsearch
		+ 要连接到Elasticsearch，必须提供一个或多个集群节点的地址。可以通过设置spring.data.elasticsearch来指定地址。一个逗号分隔的主机:端口列表的cluster-nodes属性
		+ `spring-boot-starter-data-elasticsearch`
		+ 自动注入`JestClient`、`ElasticsearchTemplate`、`TransportClient`操作,默认地址`localhost:9200`
		+ 修改配置
			+ `spring.elasticsearch.jest.uris=http://search.example.com:9200`
			`spring.elasticsearch.jest.read-timeout=10000`
			`spring.elasticsearch.jest.username=user`
			`spring.elasticsearch.jest.password=secret`
		+ 也可实现`HttpClientConfigBuilderCustomizer`实现更多定制化
	+ Solr
	+ Redis `spring-boot-starter-data-redis`
		+ 使用时自动注入`RedisConnectionFactory``StringRedisTemplate``RedisTemplate`,默认连接地址`localhost:6379`
	+ 等等
###Caching
###Messaging
1. JMS
	+ `JmsTemplate`自动注入。
2. ActiveMQ
###Calling REST Services with RestTemplate
1. 简略HttpClient的调用
###Calling REST Services with WebClient
###Validation
1. JSR-303
###Sending Email
###Distributed Transactions with JTA
###Hazelcast
###Quartz Scheduler
1. `spring-boot-starter-quartz`
###Spring Session
###Monitoring and Management over JMX
###Testing(重要)
###WebSockets
1. `详细介绍`[https://www.jianshu.com/p/4ef5004a1c81](https://www.jianshu.com/p/4ef5004a1c81 "WebSocket详解")
2. 刷新页面会导致websocket重连
3. json字符串转对象
	+ js:JSON.parse(str)
###Web Services
###过滤器&&拦截器
1. `详细介绍`[https://segmentfault.com/a/1190000012072060](https://segmentfault.com/a/1190000012072060 "SpringBoot过滤器和拦截器")
###SpringBoot定时器
1. 在入口类中加入@EnableScheduling注解：
	<pre><code>
	@SpringBootApplication
	@EnableScheduling
	public class MySpringBootApplication {
		private static Logger logger = LoggerFactory.getLogger(MySpringBootApplication.class);
		
		public static void main(String[] args) {
			SpringApplication.run(MySpringBootApplication.class, args);
			logger.info("My Spring Boot Application Started");
		}
	}
	</code></pre>
2. 创建定时器类，类中的方法注解了@Scheduled就是一个定时器：
	<pre><code>
	@Component
	public class Scheduler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void statusCheck() {    
		logger.info("每分钟执行一次。开始……");
		//statusTask.healthCheck();
		logger.info("每分钟执行一次。结束。");
	}  
 
	@Scheduled(fixedRate=20000)
	public void testTasks() {    
		logger.info("每20秒执行一次。开始……");
		//statusTask.healthCheck();
		logger.info("每20秒执行一次。结束。");
	}  
	}
	</code></pre>
3. corn表达式`https://www.cnblogs.com/xiandedanteng/p/3678650.html`
###SpringBoot定时器和WebSocket同时用出现启动异常解决方案
1. 配置类中加入如下代码
	<pre><code>
	@Bean
	public TaskScheduler taskScheduler() {
	    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	    taskScheduler.setPoolSize(10);
	    taskScheduler.initialize();
	    return taskScheduler;
	}
	</code></pre>
###@PostConstruct配置缓存
1. 使用该标签配置缓存![](https://i.imgur.com/DBvkl5i.png)
##Hibernate
1. `select new map(jxtj.CBh as CBhXttj) from A jxtj`返回的是`List<Map<String, Object>>`