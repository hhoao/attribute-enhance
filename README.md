## Development
### How to debug(IDEA)
1. 打开 `Run/Debug Configurations` -> `Add New Configuration` ->
   `JAR Application`.
2. 配置 `Path to JAR` 为服务器Jar包地址, 例如：`/home/hhoa/minecraft/test3/paper-1.19.3-386.jar`
3. 配置 `VM OPTIONS` 为 `--add-opens=java.base/java.net=ALL-UNNAMED`
4. 配置 `Working directory`为服务器Jar包工作目录, 例如: `/home/hhoa/minecraft/test3`.
5. 在下方 `Before Launch` 中 `Add New Task` -> `Run Gradle task`.
    1. 设置 `Gradle Project` 为当前Project
    2. 设置`Tasks` 为 `shadowJar`
    3. 设置 `Arguments` 为`-PjarDestinationDir=$SERVER_DIR/plugins`(替换其中的`SERVER_DIR`为服务器目录).
6. 依次确定，回到IDEA主界面，DEBUG启动.
