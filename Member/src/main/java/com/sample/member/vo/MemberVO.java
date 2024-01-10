package com.sample.member.vo;

import com.sample.member.model.MemberModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberVO toMemberVO(MemberModel memberModel) {
        MemberVO memberDTO = new MemberVO();
        memberDTO.setId(memberModel.getId());
        memberDTO.setMemberEmail(memberModel.getMemberEmail());
        memberDTO.setMemberPassword(memberModel.getMemberPassword());
        memberDTO.setMemberName(memberModel.getMemberName());
        return memberDTO;
    }
}
