package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.member.EditMemberException;
import com.quickorderservice.exception.member.NotFoundMemberException;
import com.quickorderservice.mapper.MemberMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public int joinMember(MemberDTO memberDTO) {
        if (isExistMember(memberDTO.getUserId()))
            throw new IllegalStateException("join member error");

        memberDTO.setPassword(SHA256.encBySha256(memberDTO.getPassword()));

        return memberMapper.insertMember(memberDTO);
    }

    public MemberDTO findMemberById(String userId) {
        MemberDTO findMember = memberMapper.selectMemberById(userId);

        if (findMember == null)
            throw new NotFoundMemberException();

        return findMember;
    }

    public void editMemberInfo(MemberDTO editedMemberDTO) {
        int updatedCount = memberMapper.updateMember(editedMemberDTO);
        if (updatedCount != 1)
            throw new EditMemberException("정상적으로 수정이 되지 않았습니다.");
    }

    public void editMemberPassword(String userId, String oldPassword, String newPassword) {
        MemberDTO member = findMemberByIdAndPassword(userId, oldPassword);

        String newEncryptPassword = SHA256.encBySha256(newPassword);
        if (SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
            throw new EditMemberException("기존의 비밀번호와 같습니다.");

        member.setPassword(newEncryptPassword);

        int updatedCount = memberMapper.updateMemberPassword(member);
        if (updatedCount != 1)
            throw new EditMemberException("정상적으로 수정이 되지 않았습니다.");
    }

    public int deleteMember(String userId, String password) {
        MemberDTO member = findMemberByIdAndPassword(userId, password);
        return memberMapper.deleteMember(member.getUid());
    }

    public List<MemberDTO> findAllMembers() {
        return memberMapper.selectAllMembers();
    }

    private boolean isExistMember(String userId) {
        return memberMapper.selectMemberById(userId) != null;
    }

    public MemberDTO findMemberByIdAndPassword(String userId, String password) {
        MemberDTO member = memberMapper.selectMemberByIdAndPassword(userId, SHA256.encBySha256(password));

        if (member == null)
            throw new NotFoundMemberException();

        return member;
    }

}
