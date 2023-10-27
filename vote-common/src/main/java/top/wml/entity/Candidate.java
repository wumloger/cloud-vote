package top.wml.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Candidate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private Integer votes;

    @TableField("userId")
    private Integer userId;
}
