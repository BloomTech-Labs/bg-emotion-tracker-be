package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.MemberReactions;

import java.util.List;

public interface MemberReactionService {

    List<MemberReactions> findAll();

    MemberReactions findMemberReactionById(Long id);
}
