package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ClubUsers;
import com.lambdaschool.oktafoundation.repository.ClubUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "clubusersService")
public class ClubUsersServiceImpl implements ClubUsersService{

    @Autowired
    private ClubUsersRepository clubusersrepo;

    @Override
    public List<ClubUsers> findAll() {
        List<ClubUsers> clubUsersList = new ArrayList<>();

        clubusersrepo.findAll()
                .iterator()
                .forEachRemaining(clubUsersList::add);
        return clubUsersList;
    }

    @Override
    public ClubUsers findClubUserById(Long clubuserid) throws
            ResourceNotFoundException
    {
        return clubusersrepo.findById(clubuserid)
                .orElseThrow(() -> new ResourceNotFoundException("Club User Id" + clubuserid + "not found!"));
    }
}
