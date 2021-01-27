package SpringBootTest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

// YAML 파일 매핑하기 - @Value, @ConfigurationProperties
// @ConfigurationProperties : 루트 접두사를 활용하여 원하는 객체를 바인딩해줌. 원하는 형을 선택하여 @Value보다 더 객체 지향적으로 프로퍼티 매

@Data
@Component
@ConfigurationProperties("fruit")
public class FruitProperty {
    //private List<Map> list;
    private List<Fruit> list;
    private String colorName;
}
