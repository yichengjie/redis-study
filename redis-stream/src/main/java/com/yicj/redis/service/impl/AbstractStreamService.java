package com.yicj.redis.service.impl;

import com.yicj.redis.model.vo.StreamInfoVo;
import com.yicj.redis.service.StreamService;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractStreamService implements StreamService {

    @Override
    public String add(String streamName, Map<String, Object> value) {
        StreamOperations operations = this.getStringRedisTemplate().opsForStream();
        RecordId recordId = operations.add(streamName, value) ;
        return recordId.getValue() ;
    }

    @Override
    public List<StreamInfoVo> list(String streamName) {
        StreamOperations<String,Object,Object> operations = this.getStringRedisTemplate().opsForStream() ;
        Range<String> range = Range.of(Range.Bound.inclusive("-"), Range.Bound.inclusive("+")) ;
        List<MapRecord<String, Object, Object>> list = operations.range(streamName, range);
        return list.stream().map(record -> {
            String id = record.getId().getValue();
            Map<Object,Object> value = record.getValue();
            return new StreamInfoVo(id, value) ;
        }).collect(Collectors.toList());
    }
}
