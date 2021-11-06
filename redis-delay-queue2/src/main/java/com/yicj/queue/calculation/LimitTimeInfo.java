package com.yicj.queue.calculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitTimeInfo {

    private DateTime start ;
    private DateTime end ;
}
