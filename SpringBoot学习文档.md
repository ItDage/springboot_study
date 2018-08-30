##SpringBoot开发文档
1. Application.java放到其他类的上层。
2. main()启动方法的标识@SpringBootApplication可以用@EnableAutoConfiguration和@ComponentScan替代。
3. SpringBoot配置Bean的方式,推荐第一种
	+ Java代码实现:@Configuration + @Bean
	+ 导入xml文件实现:@Configuration + @ImportResource("classpath:spring-mybatis.xml")
4. 支持远程debug。
5. maven启动SpringBoot项目:`mvn spring-boot:run`
6. `spring-boot-devtools`加入此依赖开发时会热部署,打包后自动屏蔽此依赖。`spring.devtools.restart.log-condition-evaluation-delta=false`设置了这个属性后会后热部署日志。`spring.devtools.restart.exclude=static/**,public/**`设置这些目录下文件改变不重启。

------------------Page66------------------