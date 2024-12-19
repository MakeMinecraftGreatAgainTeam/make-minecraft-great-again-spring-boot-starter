package org.mmga.spring.boot.starter.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "分页数据")
@AllArgsConstructor
public class PagerData<T> {
    @Schema(description = "总数据量")
    private long total;
    @Schema(description = "当前页数据")
    private List<T> data;
}
