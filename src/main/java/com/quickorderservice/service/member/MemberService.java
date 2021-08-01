package com.quickorderservice.service.member;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.EditException;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.mapper.MemberMapper;
import com.quickorderservice.utiles.SHA256;
import com.quickorderservice.utiles.geo.GeoData;
import com.quickorderservice.utiles.geo.Geocoding;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final Geocoding geocoding;

    public int joinMember(MemberDTO memberDTO) {
        if (isExistMember(memberDTO.getMemberId()))
            throw new DuplicatedIdException("이미 존재하는 아이디 입니다.");

        String encPassword = SHA256.encBySha256(memberDTO.getPassword());

        GeoData geoDataByAddress = geocoding.getGeoDataByAddress(memberDTO.getAddress());
        GeoData.Address[] addresses = geoDataByAddress.getAddresses();
        double lat = addresses[0].getX();
        double lon = addresses[0].getY();

        return memberMapper.insertMember(memberDTO, encPassword, lat, lon);
    }

    public MemberDTO findMemberById(String memberId) {
        MemberDTO findMember = memberMapper.selectMemberById(memberId);

        if (findMember == null)
            throw new NotFoundIdException("존재하지 않는 아이디 입니다.");

        return findMember;
    }

    public MemberDTO findMemberByUid(Long uid) {
        MemberDTO findMember = memberMapper.selectMemberByUid(uid);

        if (findMember == null)
            throw new NotFoundIdException("존재하지 않는 아이디 입니다.");

        return findMember;
    }

    public void editMemberInfo(MemberDTO editedMemberDTO) {
        int updatedCount = memberMapper.updateMember(editedMemberDTO);
        if (updatedCount != 1)
            throw new EditException("정상적으로 수정이 되지 않았습니다.");
    }

    public void editMemberPassword(Long memberUid, String oldPassword, String newPassword) {
        String memberId = findMemberByUid(memberUid).getMemberId();
        MemberDTO member = findMemberByIdAndPassword(memberId, oldPassword);

        if (member == null)
            throw new NotFoundIdException("비밀번호가 틀렸습니다.");

        String newEncryptPassword = SHA256.encBySha256(newPassword);
        if (SHA256.encBySha256(oldPassword).equals(newEncryptPassword))
            throw new EditException("기존의 비밀번호와 같습니다.");

        int updatedCount = memberMapper.updateMemberPassword(member, newEncryptPassword);
        if (updatedCount != 1)
            throw new EditException("정상적으로 수정이 되지 않았습니다.");
    }

    public int deleteMember(Long memberUid, String password) {
        String memberId = findMemberByUid(memberUid).getMemberId();
        MemberDTO member = findMemberByIdAndPassword(memberId, password);

        if (member == null)
            throw new NotFoundIdException("비밀번호가 틀렸습니다.");

        return memberMapper.deleteMember(memberUid);
    }

    public List<MemberDTO> findAllMembers() {
        return memberMapper.selectAllMembers();
    }

    private boolean isExistMember(String memberId) {
        return memberMapper.selectMemberById(memberId) != null;
    }

    public MemberDTO findMemberByIdAndPassword(String memberId, String password) {
        MemberDTO member = memberMapper.selectMemberByIdAndPassword(memberId, SHA256.encBySha256(password));
        return member;
    }

}
