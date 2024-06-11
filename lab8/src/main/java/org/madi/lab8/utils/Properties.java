package org.madi.lab8.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Properties {
    private int threadsCnt;
    private int matrixSize;
}
