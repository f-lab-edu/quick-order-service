package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.mapper.MemberMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public int joinMember(MemberDTO memberDTO) throws Exception {
        if (isExistMember(memberDTO.getId()))
            throw new IllegalAccessException("join member error");

        memberDTO.setPassword(SHA256.encBySha256(memberDTO.getPassword()));

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

        String newEncryptPassword = SHA256.encBySha256(newPassword);

        if(SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
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

    private boolean isExistMember(String id) {
        return memberMapper.selectMemberById(id) != null;
    }

    private boolean isMatchedIdAndPassword(String id, String password) throws Exception {
        MemberDTO member = memberMapper.selectMemberById(id);
        return member.getPassword().equals(SHA256.encBySha256(password));
    }

}
