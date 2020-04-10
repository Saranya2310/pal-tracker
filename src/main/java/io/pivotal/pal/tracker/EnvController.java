package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController


public class EnvController {
    private String port;
    private String memoryLimit;
    private String cfIndex;
    private String cfAddr;

    public EnvController(@Value("${port:NOT SET}") String port, @Value("${memory.limit:NOT SET}") String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}") String cfIndex, @Value("${cf.instance.addr:NOT SET}") String cfAddr){
        this.port=port;
        this.memoryLimit=memoryLimit;
        this.cfIndex=cfIndex;
        this.cfAddr=cfAddr;

    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> env = new HashMap<>();
        env.put("PORT",port);
        env.put("MEMORY_LIMIT",memoryLimit);
        env.put("CF_INSTANCE_INDEX",cfIndex);
        env.put("CF_INSTANCE_ADDR",cfAddr);
        return env;
    }
}
