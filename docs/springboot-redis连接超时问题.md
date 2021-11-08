1. 使用spring-boot-starter-data-redis时长时间不使用会出现连接超时问题
2. 启动类添加@EnableScheduling注解
3. 添加commons-pool2依赖
   ```xml
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-pool2</artifactId>
   </dependency>
   ```
4. 开启获取连接的校验
    ```java
    @Slf4j
    @Component
    public class LettuceConnectionValidConfig implements InitializingBean {
        private final RedisConnectionFactory redisConnectionFactory;
    
        public LettuceConnectionValidConfig(RedisConnectionFactory redisConnectionFactory) {
            this.redisConnectionFactory = redisConnectionFactory;
        }
    
        @Override
        public void afterPropertiesSet() throws Exception {
            if(redisConnectionFactory instanceof LettuceConnectionFactory){
                LettuceConnectionFactory c= (LettuceConnectionFactory)redisConnectionFactory;
                c.setValidateConnection(true);
            }
        }
    }
    ```
5. 配置定时任务
    ```java
    @Slf4j
    @Component
    public class LettuceConnectionValidTask   {
        private final RedisConnectionFactory redisConnectionFactory;
    
        public LettuceConnectionValidTask(RedisConnectionFactory redisConnectionFactory) {
            this.redisConnectionFactory = redisConnectionFactory;
        }
    
        @Scheduled(cron="0/2 * * * * ?")
        public void task() {
            if(redisConnectionFactory instanceof LettuceConnectionFactory){
                LettuceConnectionFactory c=(LettuceConnectionFactory)redisConnectionFactory;
                c.validateConnection();
            }
        }
    }
    ```