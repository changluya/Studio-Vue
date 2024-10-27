package com.changlu.vo.race.req;

import com.changlu.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RaceReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchYear;

}
