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
        if (!isMatchedIdAndPassword(userId, oldPassword))
            throw new IllegalStateException("edit member password error");

        String newEncryptPassword = SHA256.encBySha256(newPassword);

        if (SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
            throw new IllegalStateException("edit member password error");

        MemberDTO member = memberMapper.selectMemberById(userId);
        member.setPassword(newEncryptPassword);

        return memberMapper.updateMemberPassword(member);
    }

    public int deleteMember(String userId, String password) {
        if (!isMatchedIdAndPassword(userId, password))
            throw new IllegalStateException("delete member error");

        MemberDTO member = findMemberById(userId);
        return memberMapper.deleteMember(member.getUid());
    }

    public MemberDTO login(String userId, String password) {
        if (!isMatchedIdAndPassword(userId, password))
            throw new IllegalStateException("login member error");

        return findMemberById(userId);
    }

    public List<MemberDTO> findAllMembers() {
        return memberMapper.selectAllMembers();
    }

    private boolean isExistMember(String userId) {
        return memberMapper.selectMemberById(userId) != null;
    }

    private boolean isMatchedIdAndPassword(String userId, String password) {
        MemberDTO member = memberMapper.selectMemberById(userId);
        return member.getPassword().equals(SHA256.encBySha256(password));
    }

}
