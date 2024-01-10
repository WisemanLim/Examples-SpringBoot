package com.sample.member.model;

import com.sample.member.vo.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberModel {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberModel toMemberModel(MemberVO memberVO) {
        MemberModel memberModel = new MemberModel();
        memberModel.setMemberEmail(memberVO.getMemberEmail());
        memberModel.setMemberPassword(memberVO.getMemberPassword());
        memberModel.setMemberName(memberVO.getMemberName());
        return memberModel;
    }

    public static MemberModel toUpdateMemberModel(MemberVO memberVO) {
        MemberModel memberModel = new MemberModel();
        memberModel.setId(memberVO.getId());
        memberModel.setMemberEmail(memberVO.getMemberEmail());
        memberModel.setMemberPassword(memberVO.getMemberPassword());
        memberModel.setMemberName(memberVO.getMemberName());
        return memberModel;
    }

}
