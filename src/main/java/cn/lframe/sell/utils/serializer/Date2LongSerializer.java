package cn.lframe.sell.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * serializer  串行（化）器（把并行数据变成串行数据的寄存器）
 * 我们可以在这个类上继承的类的泛型中填写我们想要序列化的类型。
 * 这里我们想要的返回的时间戳再除以1000，所以我们可以继承这个类。
 * 然后在我们的数据传输对象OrderDTO中的日期属性上使用
 * @JsonSerialize(using = Date2LongSerializer.class)
 * 其中的Date2LongSerializer是我们的工具类。
 * @author home-pc
 * @create2018 -03 -14 -16:55
 */
public class Date2LongSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime() / 1000) ;
    }
}
