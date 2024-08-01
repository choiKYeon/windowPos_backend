package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Setting extends BaseEntity {

    //    영업 중단
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperatePause operatePause = new OperatePause();

    public void setOperatePause(OperatePause operatePause) {
        this.operatePause = operatePause;
    }

    //    영업 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperateTime operateTime = new OperateTime();

    public void setOperateTime(OperateTime operateTime) {
        this.operateTime = operateTime;
    }

    //    조리 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedCookingTime estimatedCookingTime = new EstimatedCookingTime();

    public void setEstimatedCookingTime(EstimatedCookingTime estimatedCookingTime) {
        this.estimatedCookingTime = estimatedCookingTime;
    }

    //    도착 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedArrivalTime estimatedArrivalTime = new EstimatedArrivalTime();

    public void setEstimatedArrivalTime(EstimatedArrivalTime estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    //    휴무일
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private ClosedDays closedDays = new ClosedDays();

    public void setClosedDays(ClosedDays closedDays) {
        this.closedDays = closedDays;
    }

    //    브레이크 타임
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private BreakTime breakTime = new BreakTime();

    public void setBreakTime(BreakTime breakTime) {
        this.breakTime = breakTime;
    }

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember (Member member) {
        this.member = member;
    }
}
