package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.mapper.MemberMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
            throw new IllegalStateException("find Member by id error");

        return findMember;
    }

    public int editMemberInfo(MemberDTO editedMemberDTO) {
        return memberMapper.updateMember(editedMemberDTO);
    }

    public int editMemberPassword(String userId, String oldPassword, String newPassword) {
        MemberDTO member = findMemberByIdAndPassword(userId, oldPassword);
        String newEncryptPassword = SHA256.encBySha256(newPassword);

        if (SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
            throw new IllegalStateException("edit member password error");

        member.setPassword(newEncryptPassword);

        return memberMapper.updateMemberPassword(member);
    }

    public int deleteMember(String userId, String password) {
        MemberDTO member = findMemberByIdAndPassword(userId, password);
        return memberMapper.deleteMember(member.getUid());
    }

    public MemberDTO login(String userId, String password) {
        MemberDTO member = findMemberByIdAndPassword(userId, password);
        return member;
    }

    public List<MemberDTO> findAllMembers() {
        return memberMapper.selectAllMembers();
    }

    private boolean isExistMember(String userId) {
        return memberMapper.selectMemberById(userId) != null;
    }

    private MemberDTO findMemberByIdAndPassword(String userId, String password) {
        MemberDTO member = memberMapper.selectMemberByIdAndPassword(userId, SHA256.encBySha256(password));

        if (member == null)
            throw new IllegalArgumentException("wrong Member Id or Password");

        return member;
    }

}
