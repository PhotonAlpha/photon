# 全局刷新
`curl -X POST http://localhost:3344/actuator/bus-refresh`
# 局部刷新
http://localhost:3355/actuator/bus-refresh/{destination}    
destination= {application.name}:{server.port}


`curl -X POST http://localhost:3344/actuator/bus-refresh/cloud-config-client:3355`
