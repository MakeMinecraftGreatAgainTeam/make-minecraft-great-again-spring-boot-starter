package org.mmga.spring.boot.starter.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "分页数据")
public class PagerData<T> {
    @Schema(description = "总数据量")
    private long total;
    @Schema(description = "当前页数据")
    private List<T> data;
}
