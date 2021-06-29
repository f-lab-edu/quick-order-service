package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.EditException;
import com.quickorderservice.exception.NotFoundIdException;
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
            throw new DuplicatedIdException("이미 존재하는 아이디 입니다.");

        memberDTO.setPassword(SHA256.encBySha256(memberDTO.getPassword()));

        return memberMapper.insertMember(memberDTO);
    }

    public MemberDTO findMemberById(String userId) {
        MemberDTO findMember = memberMapper.selectMemberById(userId);

        if (findMember == null)
            throw new NotFoundIdException("존재하지 않는 아이디 입니다.");

        return findMember;
    }

    public void editMemberInfo(MemberDTO editedMemberDTO) {
        int updatedCount = memberMapper.updateMember(editedMemberDTO);
        if (updatedCount != 1)
            throw new EditException("정상적으로 수정이 되지 않았습니다.");
    }

    public void editMemberPassword(String userId, String oldPassword, String newPassword) {
        MemberDTO member = findMemberByIdAndPassword(userId, oldPassword);

        String newEncryptPassword = SHA256.encBySha256(newPassword);
        if (SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
            throw new EditException("기존의 비밀번호와 같습니다.");

        member.setPassword(newEncryptPassword);

        int updatedCount = memberMapper.updateMemberPassword(member);
        if (updatedCount != 1)
            throw new EditException("정상적으로 수정이 되지 않았습니다.");
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
            throw new NotFoundIdException("아이디 혹은 비밀번호가 잘못되었습니다.");

        return member;
    }

}
