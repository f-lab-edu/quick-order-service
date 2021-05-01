package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.mapper.MemberMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    ID 중복 검사 ? ID 존재 유무

 */

@Service
@Primary
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public int joinMember(MemberDTO memberDTO) throws Exception {
        if (isExistMember(memberDTO.getId()))
            throw new IllegalAccessException("join member error");

        memberDTO.setPassword(encryptPassword(memberDTO.getPassword()));

        return memberMapper.insertMember(memberDTO);
    }

    @Override
    public MemberDTO findMemberById(String id) throws IllegalAccessException {
        MemberDTO findMember = memberMapper.selectMemberById(id);

        if (findMember == null)
            throw new IllegalAccessException("find Member by id error");

        return findMember;
    }

    @Override
    public int editMemberInfo(MemberDTO editedMemberDTO) {
        return memberMapper.updateMember(editedMemberDTO);
    }

    @Override
    public int editMemberPassword(String id, String oldPassword, String newPassword) throws Exception {
        if(!isMatchedIdAndPassword(id,oldPassword))
            throw new IllegalAccessException("edit member password error");

        String newEncryptPassword = encryptPassword(newPassword);

        if(encryptPassword(oldPassword).equals(newEncryptPassword))
            throw new IllegalAccessException("edit member password error");

        MemberDTO member = memberMapper.selectMemberById(id);
        member.setPassword(newEncryptPassword);

        return memberMapper.updateMemberPassword(member);
    }

    @Override
    public int deleteMember(String id, String password) throws Exception {
        if (!isMatchedIdAndPassword(id, password))
            throw new IllegalAccessException("delete member error");

        return memberMapper.deleteMember(id);
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public int deleteAllMember() {
        return memberMapper.deleteAllMembers();
    }

    private boolean isExistMember(String id) {
        return memberMapper.selectMemberById(id) != null;
    }

    private boolean isMatchedIdAndPassword(String id, String password) throws Exception {
        MemberDTO member = memberMapper.selectMemberById(id);

        return member.getPassword().equals(encryptPassword(password));
    }

    private String encryptPassword(String password) throws Exception {
        return SHA256.encBySha256(password);
    }
}
