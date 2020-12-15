package com.kgc.kmall.user.service;

import com.kgc.kmall.bean.Member;
import com.kgc.kmall.service.MemberService;
import com.kgc.kmall.user.mapper.MemberMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Component
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberMapper memberMapper;
    @Override
    public List<Member> selectAllMember() {
        return memberMapper.selectByExample(null);
    }
}
