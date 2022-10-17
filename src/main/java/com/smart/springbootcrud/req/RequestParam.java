package com.smart.springbootcrud.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestParam {
    public Integer pageNum;
    public Integer pageSize;

    // 模糊查询
    public String keyword;
}
